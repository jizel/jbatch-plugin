/**
 * @module JBatch
 *
 * The main entry point for the JBatch module
 *
 */
var JBatch = (function (JBatch) {

    /**
     * @property pluginName
     * @type {string}
     *
     * The name of this plugin
     */
    JBatch.pluginName = 'jbatch_plugin';

    /**
     * @property log
     * @type {Logging.Logger}
     *
     * This plugin's logger instance
     */
    JBatch.log = Logger.get('JBatch');

    /**
     * @property contextPath
     * @type {string}
     *
     * The top level path of this plugin on the server
     *
     */
    JBatch.contextPath = "/jbatch-plugin/";

    /**
     * @property templatePath
     * @type {string}
     *
     * The path to this plugin's partials
     */
    JBatch.templatePath = JBatch.contextPath + "plugin/html/";

    /**
     * @property module
     * @type {object}
     *
     * This plugin's angularjs module instance.  This plugin only
     * needs hawtioCore to run, which provides services like
     * workspace, viewRegistry and layoutFull used by the
     * run function
     */
    JBatch.module = angular.module('jbatch_plugin', ['hawtioCore'])
            .config(function ($httpProvider, $routeProvider) {
                $httpProvider.defaults.withCredentials = true;
                /**
                 * Here we define the route for our plugin.  One note is
                 * to avoid using 'otherwise', as hawtio has a handler
                 * in place when a route doesn't match any routes that
                 * routeProvider has been configured with.
                 */
                $routeProvider.
                        when('/jbatch_plugin/jobs', {
                            templateUrl: JBatch.templatePath + 'simple.html'
                        })
                        .when('/jbatch_plugin/startJob', {
                            templateUrl: JBatch.templatePath + 'startJob.html'
                        });
            });

    /**
     * Here we define any initialization to be done when this angular
     * module is bootstrapped.  In here we do a number of things:
     *
     * 1.  We log that we've been loaded (kinda optional)
     * 2.  We load our .css file for our views
     * 3.  We configure the viewRegistry service from hawtio for our
     *     route; in this case we use a pre-defined layout that uses
     *     the full viewing area
     * 4.  We configure our top-level tab and provide a link to our
     *     plugin.  This is just a matter of adding to the workspace's
     *     topLevelTabs array.
     */
    JBatch.module.run(function (workspace, viewRegistry, layoutFull, $rootScope, $http) {

        JBatch.log.info(JBatch.pluginName, " loaded");

        Core.addCSS(JBatch.contextPath + "plugin/css/simple.css");

        // tell the app to use the full layout, also could use layoutTree
        // to get the JMX tree or provide a URL to a custom layout
        viewRegistry["jbatch_plugin"] = layoutFull;

//        var id = document.cookie.split(';')[0].split('=')[1];
//        $http.defaults.headers.common.Authorization = document.cookie ;
//        //or try this 
//        $http.defaults.headers.common['Cookie'] = document.cookie;
//        $http.defaults.headers.common['Auth-Token'] = document.cookie;
//        $http.defaults.withCredentials = true;

        /* Set up top-level link to our plugin.  Requires an object
         with the following attributes:
         
         id - the ID of this plugin, used by the perspective plugin
         and by the preferences page
         content - The text or HTML that should be shown in the tab
         title - This will be the tab's tooltip
         isValid - A function that returns whether or not this
         plugin has functionality that can be used for
         the current JVM.  The workspace object is passed
         in by hawtio's navbar controller which lets
         you inspect the JMX tree, however you can do
         any checking necessary and return a boolean
         href - a function that returns a link, normally you'd
         return a hash link like #/foo/bar but you can
         also return a full URL to some other site
         isActive - Called by hawtio's navbar to see if the current
         $location.url() matches up with this plugin.
         Here we use a helper from workspace that
         checks if $location.url() starts with our
         route.
         */
        workspace.topLevelTabs.push({
            id: "jbatch",
            content: "JBatch",
            title: "JBatch plugin loaded dynamically",
            isValid: function (workspace) {
                return true;
            },
            href: function () {
                return "#/jbatch_plugin/jobs";
            },
            isActive: function (workspace) {
                return workspace.isLinkActive("jbatch_plugin/jobs");
            }

        });

    });

    /**
     * @function JBatchController
     * @param $scope
     * @param jolokia
     * @param $http
     * @param toastr
     *
     * The controller for simple.html, only requires the jolokia
     * service from hawtioCore
     *
     */
//    angular.module('myApp.services').factory('Entry', function ($resource) {
//        return $resource('http://localhost:8080/jbatch-plugin/rest/jobs/names'); // Note the full endpoint address
//    });

    JBatch.BatchJobController = function ($scope, $http, toastr, $timeout) {
        $scope.jobs = "";
        $scope.allInstances = "";
        $scope.allInstances2 = "";
        $scope.jobCounts = 0;
        $scope.selected_jobname = "";
        $scope.selected_instances = "";
        $scope.selected_instance_id;
        $scope.selected_executions = "";
        $scope.selected_execution_id;
        $scope.last_execution_id = 0;
        $scope.selected_steps = "";
        $scope.deployments = "";
        $scope.batchDeployments = "";
        $scope.selected_depl = "";
        $scope.run_job = "";
        $scope.actions = ['stop', 'abandon'];
        $scope.selectedAction = {};
        $scope.executionsWatcher = new String("");
        $scope.abandonWatcher = new String("");
        $scope.stopWatcher = new String("");
        seconds2launch = 0;

//        Methods consuming the REST resources
        $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/names", {
        }).then(function (resp) {
            $scope.jobs = resp.data;
        });

        $scope.getJobCounts = function () {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/counts", {
            }).then(function (resp) {
                $scope.jobCounts = resp.data;
            });
        };

        $scope.getDeployments = function () {
            $http.get("http://localhost:8080/jbatch-plugin/rest/cli/deployments/").then(function (resp) {
                $scope.deployments = resp.data;
            });
        };

        $scope.getBatchDeployments = function () {
            $http.get("http://localhost:8080/jbatch-plugin/rest/cli/batchDepl/", {
                withCredentials: true
            }).then(function (resp) {
                $scope.batchDeployments = resp.data;
            });
        };

        $scope.setSelectedInstances = function (jobname) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/inst/" + jobname
                    ).then(function (resp) {
                $scope.selected_instances = resp.data;
                $scope.selected_jobname = jobname;
//           JBatch.log.info($scope.selected_instances);
            });
        };

        $scope.setSelectedExecutions = function (jobInstanceId) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/inst/" + $scope.selected_jobname + "/" + jobInstanceId + "/" + "executions").then(function (resp) {
                $scope.selected_executions = resp.data;
                $scope.selected_instance_id = jobInstanceId;
//           JBatch.log.info($scope.selected_executions);
            });
        };

        $scope.getLastExecution = function (jobInstanceId) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/inst/" + $scope.selected_jobname + "/" + jobInstanceId + "/" + "executions/last").then(function (resp) {
                $scope.last_execution_id = resp.data;
            });
        };

        $scope.setSelectedSteps = function (execId) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/steps/" + execId).then(function (resp) {
                $scope.selected_steps = resp.data;
                $scope.selected_execution_id = execId;
//           JBatch.log.info("Steps selected for execution no: " + execId);
            });
        };

        $scope.restartExecution = function (execId) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/restart/" + execId).then(function (resp) {
                var jsonResp = resp.data;
                if (jsonResp.outcome.toString() === "failed") {
                    JBatch.log.error("Restarting job with id: " + execId + " failed. Failure description: " + jsonResp['description']);
                } else {
                    $scope.logAndToastSuccess("Job with id: " + execId + " restarted. New id:" + jsonResp.result);
                    $scope.refreshSelectedExecutions();
                }
            });
        };

        $scope.startJobCli = function (deploymentName, jobName, properties) {
            var propertiesStr = new String(properties);
            if (propertiesStr == "undefined" || propertiesStr.length == 0) {
                $http.get("http://localhost:8080/jbatch-plugin/rest/cli/start/" + deploymentName + "/" + jobName, {
//                    headers: {'Auth-Token': document.cookie}                    
                }).then(function (resp) {
                    var jsonResp = resp.data;
                    if (jsonResp.outcome.toString() === "failed") {
                        JBatch.log.error("Job " + jobName + " start failed. Failure description: " + jsonResp['failure-description']);
                    } else {
                        $scope.logAndToastSuccess("Job started: " + jobName + " with id: " + jsonResp.result);
                        $scope.getJobCounts();
                    }
                });

//                Start Job with properties
            } else {
                $http.get("http://localhost:8080/jbatch-plugin/rest/cli/start/" + deploymentName + "/" + jobName + "/" + properties).then(function (resp) {
                    var jsonResp = resp.data;
                    if (jsonResp.outcome.toString() === "failed") {
                        JBatch.log.error("Job " + jobName + " start failed. Failure description: " + jsonResp['failure-description']);
                    } else {
                        $scope.logAndToastSuccess("Job started: " + jobName + "with id: " + jsonResp.result);
                        $scope.getJobCounts();
                    }
                });
            }
        };

        $scope.scheduleJobCli = function (deploymentName, jobName, properties) {
            $scope.getSecondsFromDate();
            $timeout(function () {
                $scope.startJobCli(deploymentName, jobName, properties);
            }, $scope.miliseconds2launch);
        };

        $scope.setJob2run = function (deploymentName, jobName) {
            $scope.selected_depl = deploymentName;
            $scope.run_job = jobName;
            JBatch.log.info("Job selected: " + jobName + "from: " + deploymentName);
        };

        $scope.stopExecution = function (executionId) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/stop/" + executionId).then(function (resp) {
                var jsonResp = resp.data;
                if (jsonResp['outcome'] == "failed") {
                    if (jsonResp.description.toString().includes("is not running")) {
                        JBatch.log.error("Cannot stop execution that is not running!");
                    } else {
                        JBatch.log.error("Stopping job with id: " + executionId + " failed. Failure description: " + jsonResp['description']);
                    }
                } else {
                    $scope.logAndToastSuccess("Job with id: " + executionId + " stopped.");
                    $scope.refreshSelectedExecutions();
                }
            });
        };

        $scope.abandonExecution = function (executionId) {
            $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/abandon/" + executionId).then(function (resp) {
//                $scope.executionsWatcher = new String(resp.data);
//                JBatch.log.info("Abandon execution with id: " + executioId + " . Result: " + resp.data);
                var jsonResp = resp.data;
                if (jsonResp['outcome'] == "failed") {
                    if (jsonResp.description.toString().includes("running and cannot be abandoned")) {
                        JBatch.log.error("Cannot abandon running execution!");
                    } else {
                        JBatch.log.error("Restarting job with id: " + executionId + " failed. Failure description: " + jsonResp['description']);
                    }
                } else {
                    $scope.logAndToastSuccess("Job with id: " + executionId + " abandoned.");
                    $scope.refreshSelectedExecutions();
                }
            });
            $scope.refreshSelectedExecutions();
        };

//        Actions called from dropdown on each execution
        $scope.actionCalledOnExec = function (executionId, action) {
            if (action === "stop") {
                $scope.stopExecution(executionId);
            } else if (action === "restart") {
                $scope.restartExecution(executionId);
            } else if (action === "abandon") {
                $scope.abandonExecution(executionId);
            } else {
                JBatch.log.error("Invalid action: " + action);
            }
        };

        //        Init
        $scope.getJobCounts();
        $scope.getDeployments();
        $scope.getBatchDeployments();

//        Help methods
        $scope.refreshSelectedExecutions = function () {
            $scope.setSelectedExecutions($scope.selected_instance_id);
        };

        $scope.$watch('executionsWatcher', function () {
            $scope.refreshSelectedExecutions();
            JBatch.log.info("Last execution id updated. New id: " + $scope.executionsWatcher);
        });

        $scope.logAndToastSuccess = function (msg) {
            JBatch.log.info(msg);
            toastr.success(msg);
        };
        $scope.getSecondsFromDate = function () {
//            $scope.datetime
            var currDate = new Date();
            var offset = currDate.getTimezoneOffset();
            var launchDate = new Date($scope.datetime);
            launchDate.setHours(launchDate.getHours() + offset / 60);
            var delay = launchDate.getTime() - currDate.getTime();
//            JBatch.log.error("Launch date: " + launchDate + ", currdate: " + currDate + ", datetime: " + $scope.datetime + ", offset: " + offset); 
            if (delay > 0) {
                $scope.miliseconds2launch = delay;
            } else {
                JBatch.log.error("Time of launch must take place in the future!");
            }
            return delay;
        };

    };

    return JBatch;

})(JBatch || {});

// tell the hawtio plugin loader about our plugin so it can be
// bootstrapped with the rest of angular
hawtioPluginLoader.addModule(JBatch.pluginName);

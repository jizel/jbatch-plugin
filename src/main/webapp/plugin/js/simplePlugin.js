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
    JBatch.module = angular.module('jbatch_plugin', ['hawtioCore', 'ngResource'])
            .config(function ($routeProvider) {

                /**
                 * Here we define the route for our plugin.  One note is
                 * to avoid using 'otherwise', as hawtio has a handler
                 * in place when a route doesn't match any routes that
                 * routeProvider has been configured with.
                 */
                $routeProvider.
                        when('/jbatch_plugin', {
                            templateUrl: JBatch.templatePath + 'simple.html'
                        });
            });
            
//            Doesn't seem to help
//    JBatch.module.factory('Entry', function ($resource) {
//        return $resource('http://localhost:8080/jbatch-plugin/rest/jobs/names'); // Note the full endpoint address
//    });
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
    JBatch.module.run(function (workspace, viewRegistry, layoutFull, $rootScope, $resource) {

        JBatch.log.info(JBatch.pluginName, " loaded");

        Core.addCSS(JBatch.contextPath + "plugin/css/simple.css");

        // tell the app to use the full layout, also could use layoutTree
        // to get the JMX tree or provide a URL to a custom layout
        viewRegistry["jbatch_plugin"] = layoutFull;

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
                return "#/jbatch_plugin";
            },
            isActive: function (workspace) {
                return workspace.isLinkActive("jbatch_plugin");
            }

        });

    });

    /**
     * @function JBatchController
     * @param $scope
     * @param jolokia
     *
     * The controller for simple.html, only requires the jolokia
     * service from hawtioCore
     *
     */
//    angular.module('myApp.services').factory('Entry', function ($resource) {
//        return $resource('http://localhost:8080/jbatch-plugin/rest/jobs/names'); // Note the full endpoint address
//    });

    JBatch.SimpleController = function ($scope, jolokia, $http, $resource) {
        $scope.hello = "Hello world!";
        $scope.jobs = "";
        $scope.jobNamesRes="";
        $scope.jobCount3 = 0;
        $scope.allInstances = "";
        $scope.allInstances2 = "";
        
        $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/names").then(function (resp) {
            $scope.jobs = resp.data;
        });
        angular.forEach($scope.jobs, function(job){
            
        });
        
        $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/count/" + 'restReader' ).then(function (resp) {
            $scope.jobcount2 = resp.data;
        });
        
        $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/counts").then(function (resp) {
            $scope.jobCounts = resp.data;
        });

        $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/tst").then(function (resp) {
           $scope.allInstances = resp.data; 
        });
        
        $http.get("http://localhost:8080/jbatch-plugin/rest/jobs/tst2").then(function (resp) {
           $scope.allInstances2 = resp.data; 
        });


        // register a watch with jolokia on this mbean to
        // get updated metrics
        Core.register(jolokia, $scope, {
            type: 'read', mbean: 'java.lang:type=OperatingSystem',
            arguments: []
        }, onSuccess(render));

        // update display of metric
        function render(response) {
            $scope.cpuLoad = response.value['ProcessCpuLoad'];
            Core.$apply($scope);
        }
        function getJobs($scope, $http) {
            $http.get('http://localhost:8080/jbatch-plugin/jobs/test/names').success(function (data) {
                return data;
            });
        }

// var JobNames = $resource('http://localhost\\:8080/jbatch-plugin/rest/jobs/names',{
//          get: { method: 'GET', isArray: true }  
//        });
//        JobNames.get(function(data){
//            $scope.jobNamesRes = data;
//        });
//       
//        var JobCount = $resource('http://localhost\\:8080/jbatch-plugin/rest/jobs/count/:jobname',{
//             get: { method: 'GET', params: {}, isArray: true }
//        });
//        JobCount.get({
//        jobname:'restReader'
//        },function(data){
//            $scope.jobCount3 = data;
//        });

    };

    return JBatch;

})(JBatch || {});

// tell the hawtio plugin loader about our plugin so it can be
// bootstrapped with the rest of angular
hawtioPluginLoader.addModule(JBatch.pluginName);

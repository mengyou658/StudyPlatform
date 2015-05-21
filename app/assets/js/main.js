/**
 * Created by maximcherkasov on 09.05.15.
 */

(function (requirejs) {
    'use strict';

    // -- RequireJS config --
    requirejs.config({
        // Packages = top-level folders; loads a contained file named 'main.js"
        packages: ['home'
                ,'dashboard'
                ,'common'
                ,'profile'
                ,'services'
                ,'auth'],
        shim: {
            'jsRoutes': {
                deps: [],
                // it's not a RequireJS module, so we have to tell it what var is returned
                exports: 'jsRoutes'
            },
            'angular': {
                deps: ['jquery'],
                exports: 'angular'
            },
            'flat-ui': ['jquery'],
            'angular-route': ['angular'],
            'bootstrap': ['jquery']
            , 'satellizer': ['angular']
        },
        paths: {
            'requirejs': ['../lib/requirejs/require'],
            'jquery': ['../lib/jquery/jquery'],
            'angular': ['../lib/angularjs/angular'],
            'angular-route': ['../lib/angularjs/angular-route'],
            'bootstrap': ['../lib/bootstrap/js/bootstrap'],
            'domReady': '../lib/requirejs-domready/domReady',
            'uiRouter': ['../lib/angular/angular-ui-router'],
            'jsRoutes': ['/jsroutes']
            ,'flat-ui': ['../bower_components/flat-ui/dist/js/flat-ui']
            ,'satellizer': ['../lib/satellizer/satellizer']
        }
    });

    requirejs.onError = function (err) {
        console.log(err);
    };

    // Load the app. This is kept minimal so it doesn't need much updating.
    require(['angular', 'angular-route', 'jquery', 'flat-ui', './app', 'satellizer'],
        function (angular) {
            angular.bootstrap(document, ['app']);
        }
    );
})(requirejs);

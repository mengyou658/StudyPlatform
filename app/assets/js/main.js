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
                ,'settings'
                ,'flipCards'
                ],
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
            'flat-ui': ['jquery']
            , 'angular-route': ['angular']
            , 'bootstrap': ['jquery']
            , 'satellizer': ['angular']
            , 'angular-ui-tpls' : {
                deps: ['angular-ui']
            }
            , 'angular-ui' : ['angular']
            , 'angular-ui-router' : ['angular-ui']
            , 'angular-ui-router-tabs': ['angular-ui-router']
        },
        paths: {
            'requirejs': ['../lib/requirejs/require'],
            'jquery': ['../lib/jquery/jquery'],
            'angular': ['../lib/angularjs/angular'],
            //'angular-route': ['../lib/angularjs/angular-route'],
            'bootstrap': ['../lib/bootstrap/js/bootstrap'],
            'domReady': '../lib/requirejs-domready/domReady',
            'jsRoutes': ['/jsroutes']
            ,'flat-ui': ['../bower_components/flat-ui/dist/js/flat-ui']
            ,'satellizer': ['../lib/satellizer/satellizer']
            ,'angular-ui': ['../lib/angular-ui-bootstrap/ui-bootstrap']
            ,'angular-ui-tpls': ['../lib/angular-ui-bootstrap/ui-bootstrap-tpls']
            ,'angular-ui-router': ['../lib/angular-ui-router/angular-ui-router']
            ,'angular-ui-router-tabs': ['../bower_components/angular-ui-router-tabs/src/ui-router-tabs']
        }
    });

    requirejs.onError = function (err) {
        console.log(err);
    };

    // Load the app. This is kept minimal so it doesn't need much updating.'angular-route',
    require(['angular',  'jquery', 'flat-ui', 'angular-ui-router', './app'],
        function (angular) {
            angular.bootstrap(document, ['app']);
        }
    );
})(requirejs);

/**
 * Created by maximcherkasov on 09.05.15.
 */

(function (requirejs) {
    'use strict';

    // -- RequireJS config --
    requirejs.config({
        // Packages = top-level folders; loads a contained file named 'main.js"
        packages: [
                'home'
                ,{
                    name: 'home.sets',
                    location: 'home/sets'
                }
                ,'header'
                ,'classes'
                ,'dashboard'
                ,'common'
                ,'profile'
                ,'services'
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
            }
            , 'flat-ui': ['jquery']
            //, 'angular-route': ['angular']
            , 'bootstrap': ['jquery']
            , 'satellizer': ['angular']
            , 'angular-ui-tpls' : {
                deps: ['angular-ui']
            }
            , 'angular-ui' : ['angular']
            , 'ui-select' : ['angular']
            , 'angular-ui-router' : ['angular-ui']
            , 'angular-breadcrumb' : ['angular-ui-router']
            , 'angular-animate' : ['angular']
            , 'angular-xeditable' : ['angular-ui']
            , 'angular-sanitize' : ['angular']
            //, 'angular-datatables': {
            //    deps: ['jquery', 'angular']
            //    }
            },
        paths: {
            'requirejs': ['../lib/requirejs/require'],
            'jquery': ['../lib/jquery/jquery'],
            'angular': ['../lib/angularjs/angular'],
            //'angular-route': ['../lib/angularjs/angular-route'],
            'bootstrap': ['../lib/bootstrap/js/bootstrap'],
            'domReady': '../lib/requirejs-domready/domReady',
            'jsRoutes': ['/jsroutes']
            ,'satellizer': ['../lib/satellizer/satellizer']
            ,'angular-ui': ['../lib/angular-ui-bootstrap-bower/ui-bootstrap']
            ,'angular-ui-tpls': ['../lib/angular-ui-bootstrap-bower/ui-bootstrap-tpls']
            ,'angular-ui-router': ['../lib/angular-ui-router/release/angular-ui-router']
            ,'ui-select': ['../lib/ui-select/dist/select']
            ,'angular-breadcrumb': ['../lib/angular-breadcrumb/dist/angular-breadcrumb']
            ,'angular-xeditable': ['../lib/angular-xeditable/dist/js/xeditable']
            ,'angular-animate': ['../lib/angular-animate/angular-animate']
            ,'angular-sanitize': ['../lib/angular-sanitize/angular-sanitize']
        }
    });

    requirejs.onError = function (err) {
        console.log(err);
    };

    // Load the app. This is kept minimal so it doesn't need much updating.'angular-route',
    require(['angular',  'jquery', 'ui-select', 'angular-ui-router',
             'angular-ui-tpls', 'angular-breadcrumb', 'angular-animate',
            'angular-xeditable','angular-animate','angular-sanitize', './app'],
        function (angular) {
            angular.bootstrap(document, ['app', 'ncy-angular-breadcrumb']);
        }
    );
})(requirejs);


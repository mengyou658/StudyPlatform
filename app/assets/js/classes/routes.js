/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('classes.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('classes',
            {
                url: '/classes',
                templateUrl: '/assets/partials/classes/index.html',
                controller:controllers.ClassesCtrl,
                ncyBreadcrumb: {
                    label: 'Classes'
                }
            });
    }]);
    return mod;
});
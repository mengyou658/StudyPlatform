/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('home.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('sets', {
                url: '/sets',
                reload: true,
                templateUrl: '/assets/partials/index.html',
                controller:controllers.HomeCtrl,
                ncyBreadcrumb: {
                    label: 'Your Sets'
                }
            });
    }]);
    return mod;
});
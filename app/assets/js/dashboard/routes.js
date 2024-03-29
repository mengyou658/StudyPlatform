/**
 * Created by maximcherkasov on 09.05.15.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('dashboard.routes', ['rema7.common']);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('dashboard', {url: '/dashboard',  templateUrl: '/assets/partials/dashboard/index.html',  controller:controllers.DashboardCtrl});
    }]);
    return mod;
});
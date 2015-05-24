/**
 * Created by maximcherkasov on 10.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('settings.products.routes', []);
    mod.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/settings',  {templateUrl: '/assets/partials/settings/products.html',  controller:controllers.SettingsProductsCtrl});
    }]);

    return mod;
});
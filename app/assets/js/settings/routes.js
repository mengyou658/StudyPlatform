/**
 * Created by maximcherkasov on 10.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('settings.products.routes', ['ui.router']);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('settings',
            {templateUrl: '/assets/partials/settings/settings.html',  controller:controllers.SettingsProductsCtrl})
            .state('settings.products',
            {url: '/settings/products', templateUrl: '/assets/partials/settings/products.html'})
            .state('settings.flipcards',
            {url: '/settings/flipcards', templateUrl: '/assets/partials/settings/flipcards.html'});
    }]);

    return mod;
});
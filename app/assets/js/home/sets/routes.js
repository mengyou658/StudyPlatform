/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './controllers'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('home.sets.routes', []);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('sets.set',
            {
                url: '/:setId',
                templateUrl: '/assets/partials/sets/index.html',
                controller:controllers.CardsSetCtrl,
                ncyBreadcrumb: {
                    label: 'Set {{set.name}}'
                }
            })
            .state('sets.set.edit',
            {
                url: '/edit',
                templateUrl: '/assets/partials/sets/edit.html',
                controller:controllers.CardsSetEditCtrl,
                ncyBreadcrumb: {
                    label: 'Edit'
                }
            }
        );
    }]);
    return mod;
});
/**
 * Created by maximcherkasov on 09.05.15.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('flipCards.routes', ['rema7.common']);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('flipcards', {url: '/flipcards',  templateUrl: '/assets/partials/flipCards/index.html',  controller:controllers.FlipCardsCtrl});
    }]);
    return mod;
});
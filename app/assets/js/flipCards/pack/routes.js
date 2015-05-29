/**
 * Created by maximcherkasov on 09.05.15.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
    'use strict';

    var mod = angular.module('flipCards.pack.routes', ['rema7.common']);
    mod.config(['$stateProvider', function($stateProvider) {
        $stateProvider
            .state('flipcards.pack', {
                url: "/pack/:packId",
                views: {
                    "flashCardsView": { templateUrl: '/assets/partials/flipCards/pack/index.html', controller:controllers.FlipCardsPackCtrl }

                }
            });
    }]);
    return mod;
});
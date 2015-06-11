/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var FlipCardsCtrl = function($scope, $http, $modal) {
        $scope.items = {};

        $http.get('/flip_cards/packs')
            .success(function(data) {
                $scope.items = data;
            });

        var openModal = function(item) {
            if (item === undefined)
                item = {shared: false};

            return  $modal.open({
                templateUrl: '/assets/partials/flipCards/modal.html',
                windowClass: 'manage-cards-modal',
                controller: ModalInstanceCtrl,
                resolve: {
                    data: function () {
                        return item;
                    }
                }
            });
        };

        $scope.create = function () {
            openModal().result.then(function (data) {
                 $scope.items.push(data);
            });
        };

        $scope.edit = function (item) {
            openModal(item).result.then(function (data) {
            });
        };

        $scope.remove = function (item) {
            //$http.get('/product')
            //    .success(function(data) {
            //        $scope.items = data;
            //    });
        };
    };

    var ModalInstanceCtrl = function ($scope, $http, $modalInstance, data) {
        console.log(data);
        $scope.item = data;


        $scope.create = function () {
            $http.post('/flip_cards/packs', $scope.item)
                .success(function(data) {
                    $modalInstance.close(data);
                }).error(function(data, status, headers, config) {
                    console.log(data)
                });
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };
    ModalInstanceCtrl.$inject = ['$scope', '$http', '$modalInstance', 'data' ];

    FlipCardsCtrl.$inject = [ '$scope', '$http', '$modal' ];

    return {
        FlipCardsCtrl : FlipCardsCtrl
    };

});
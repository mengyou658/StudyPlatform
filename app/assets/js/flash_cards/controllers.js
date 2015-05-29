/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var FlashCardsCtrl = function($scope, $http, $modal, DTOptionsBuilder, DTColumnDefBuilder) {
        $scope.items = {};

        $http.get('/flash_cards')
            .success(function(data) {
                $scope.items = data;
            });

        $scope.dtOptions = DTOptionsBuilder.newOptions()
            .withPaginationType('full_numbers').withOption('paging', false);

        $scope.dtColumnDefs = [
            DTColumnDefBuilder.newColumnDef(0),
            DTColumnDefBuilder.newColumnDef(1),
            DTColumnDefBuilder.newColumnDef(2),
            DTColumnDefBuilder.newColumnDef(2).notSortable()
        ];

        var openModal = function(item) {
            return  $modal.open({
                templateUrl: '/assets/partials/flash_cards/modal.html',
                windowClass: 'manage-cards-modal',
                controller: ModalInstanceCtrl,
                resolve: {
                    data: function () {
                        return {
                            editFlag: item !== undefined,
                            item: item === undefined ? {} : item
                        };
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

    FlashCardsCtrl.$inject = [ '$scope', '$http', '$modal', 'DTOptionsBuilder', 'DTColumnDefBuilder' ];

    var ModalInstanceCtrl = function ($scope, $http, $modalInstance, data) {
        console.log(data);
        $scope.item = data.item;
        $scope.editFlag = data.editFlag;


        $scope.create = function () {
            $http.post('/flash_cards', $scope.item)
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


    return {
        FlashCardsCtrl : FlashCardsCtrl
    };

});
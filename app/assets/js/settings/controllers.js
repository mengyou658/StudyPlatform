/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var SettingsProductsCtrl = function($scope, $modal, $http) {
        $scope.products = {};

        $scope.tabData   = [
            {
                heading: 'Products',
                route:   'settings.products'
            },
            {
                heading: 'FlipCard Lists',
                route:   'settings.flipcards'
            }
        ];

        $http.get('/product')
            .success(function(data) {
                $scope.products = data;
            });

        var openModal = function(item) {
            if (item === undefined)
                item = {};

            return  $modal.open({
                templateUrl: '/assets/partials/settings/modal.html',
                windowClass: 'manage-product-modal',
                controller: ModalInstanceCtrl,
                resolve: {
                    data: function () {
                        return item;
                    }
                }
            });
        };

        $scope.create = function () {
            openModal().result.then(function (product) {
                $http.post('/product', product)
                    .success(function(data) {
                        $scope.products.push(data);
                    })
            });
        };

        $scope.edit = function (item) {
            openModal(item).result.then(function (product) {
            });
        };

        $scope.remove = function (item) {
            $http.get('/product')
                .success(function(data) {
                    $scope.products = data;
                });
        };
    };

    SettingsProductsCtrl.$inject = [ '$scope', '$modal', '$http', '$auth',  'playRoutes', 'rpcService' ];

    var ModalInstanceCtrl = function ($scope, $modalInstance, data) {
        console.log(data);
        $scope.product = data;

        $scope.save = function () {
            $modalInstance.close($scope.product);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    };

    ModalInstanceCtrl.$inject = ['$scope', '$modalInstance', 'data' ];

    return {
        SettingsProductsCtrl : SettingsProductsCtrl
    };

});
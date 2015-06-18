/**
 * Created by m.cherkasov on 15.05.15.
 */


define(['angular'], function(angular) {
    'use strict';

    return angular.module('rema7.services', ['ui.router']).factory('rpcService',function(){
        var sharedService = {};

        sharedService.customer = {};

        sharedService = {

            getRequest : function(id, method, params) {
                return JSON.stringify({
                    id: id.toString(),
                    jsonrpc: "2.0",
                    method: method,
                    params: params === undefined ? {} : params
                })
            }
        };

        return sharedService;
    }).factory('modalService', function($modal) {
        var templateUrl;
        var windowClass;
        var controller;
        var openModal = function(item) {
            return $modal.open({
                templateUrl: templateUrl,
                windowClass: windowClass,
                controller: ModalCardsSetCtrl,
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

        var ModalCardsSetCtrl = function ($scope, $http, $modalInstance, data) {
            //console.log(data);
            if (data.item !== undefined) {
                $scope.item = {
                    id: parseInt(data.item.id),
                    name: data.item.name,
                    description: data.item.description
                };
            }

            $scope.editFlag = data.editFlag;

            $scope.save = function () {
                $http.post('/sets', $scope.item)
                    .success(function(data) {
                        $modalInstance.close(data);
                    }).error(function(data, status, headers, config) {
                        console.log(data)
                    });
            };

            $scope.remove = function () {
                console.log($scope.item);

                $http.delete('/sets/'+$scope.item.id)
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

        ModalCardsSetCtrl.$inject = ['$scope', '$http', '$modalInstance', 'data' ];

        return {
            initAsCardsSet: function(data) {
                if (data === undefined)
                    throw new Error('data === undefined');

                templateUrl = data.templateUrl;
                windowClass = data.windowClass;
                controller = ModalCardsSetCtrl
            },
            create: function () {
                return openModal();
            },
            edit: function (item) {
                return openModal(item);
            }
        }
    });
});
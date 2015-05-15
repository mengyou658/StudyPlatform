/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var AuthCtrl = function($scope, playRoutes, rpcService) {
        //$scope.user = {};

        //console.log(rpcService.getRequest);
        //
        //var profileUrl = playRoutes.controllers.Profile.list();
        //
        //profileUrl.post(rpcService.getRequest(1, "testRpc")
        //   ).
        //    success(function(data, status, headers, config) {
        //        $scope.user.name = data;
        //        console.log(data);
        //    }).
        //    error(function(data, status, headers, config) {
        //        // called asynchronously if an error occurs
        //        // or server returns response with an error status.
        //    });
    };

    AuthCtrl.$inject = [ '$scope', 'playRoutes', 'rpcService' ];

    return {
        AuthCtrl : AuthCtrl
    };

});
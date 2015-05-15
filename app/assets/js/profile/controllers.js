/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var ProfileCtrl = function($scope, playRoutes, rpcService) {
        $scope.user = {};

        console.log(rpcService.getRequest);

        var profileUrl = playRoutes.controllers.Profile.list();

        profileUrl.post(rpcService.getRequest(1, "testRpc")
           ).
            success(function(data, status, headers, config) {
                $scope.user.name = data;
                console.log(data);
            }).
            error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    ProfileCtrl.$inject = [ '$scope', 'playRoutes', 'rpcService' ];

    return {
        ProfileCtrl : ProfileCtrl
    };

});
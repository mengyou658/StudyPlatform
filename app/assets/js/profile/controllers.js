/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var ProfileCtrl = function($scope, playRoutes, $window) {
        $scope.user = { name: 'ddd'};

        var profileUrl = playRoutes.controllers.Profile.list();
        profileUrl.post(
            JSON.stringify({
                id: 1,
                jsonrpc: "2.0",
                method: "testRpc1",
                params: {
                    input: "TestParam1"
                }
            })
        ).
            success(function(data, status, headers, config) {
                $scope.user.name = data
                console.log(data);
            }).
            error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    ProfileCtrl.$inject = [ '$scope', 'playRoutes', '$window' ];

    return {
        ProfileCtrl : ProfileCtrl
    };

});
/**
 * Created by maximcherkasov on 10.05.15.
 */
define([  ], function() {
    'use strict';

    var ProfileCtrl = function($scope, playRoutes, $window) {
        $scope.user = { name: 'ddd'};

        var profileUrl = playRoutes.controllers.Profile.list();
        console.log(profileUrl);
        //console.log(clusterNodesSocketUrl.webSocketUrl('asd'));

        var clusterNodesWS = new WebSocket(profileUrl.webSocketUrl());

        clusterNodesWS.onmessage = function(msg) {
            $scope.$apply(function() {
                var node = JSON.parse(msg.data);
                console.log(node);
                console.log($scope.user.name);
                $scope.user.name = node;
                console.log($scope.user.name);
            });
            //if (node.state == 'removed') {
            //    delete nodes[node.address];
            //} else {
            //    nodes[node.address] = node;
            //}
            //$scope.$apply(function() {
            //    $scope.nodes = _.values(nodes);
            //});
        };

        clusterNodesWS.onerror = function(msg) {

            console.log(msg);
            $window.location = '/';
        };
        $scope.user.name = "zzz";

        clusterNodesWS.addEventListener('open', onOpen, false);
        function onOpen() {
            clusterNodesWS.send(JSON.stringify({
                id: 1,
                jsonrpc: "2.0",
                method: "testRpc1",
                params: {
                    input: "TestParam1"
                }
            }));
        }

        //profileUrl.post('{"aaa": "aaa"}').
        //    success(function(data, status, headers, config) {
        //        $scope.user.name = data
        //        console.log(data);
        //    }).
        //    error(function(data, status, headers, config) {
        //        // called asynchronously if an error occurs
        //        // or server returns response with an error status.
        //    });
    };

    ProfileCtrl.$inject = [ '$scope', 'playRoutes', '$window' ];

    return {
        ProfileCtrl : ProfileCtrl
    };

});
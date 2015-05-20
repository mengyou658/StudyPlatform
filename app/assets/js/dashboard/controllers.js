/**
 * Created by maximcherkasov on 09.05.15.
 */

define([], function() {
    'use strict';

    var DashboardCtrl = function($scope, $http) {
        $scope.metrics = [];
        $scope.nodes = [];

        /** Creating the websocket to watch for cluster node changes */
        //var clusterNodesSocketUrl = playRoutes.controllers.Application.pr();
        //var clusterNodesWS = new WebSocket(clusterNodesSocketUrl);

        var callbackName = 'instajam' + Math.round(new Date().getTime() / 1000) + Math.floor(Math.random() * 100);
        var authUrl = 'https://instagram.com/oauth/authorize/?client_id='
            + '19dd284d24b04e7182b142ede16324b8' + '&redirect_uri='
            + 'http://localhost:9000' + '&response_type=token&callback='+callbackName;
        //authUrl = 'https://api.instagram.com/v1/media/popular?client_id=642176ece1e7445e99244cec26f4de1f&callback=JSON_CALLBACK';
        console.log(authUrl);

        $http.jsonp(authUrl).success(function(response) {
            //console.log(response.data)
        });
        //+ (this.scope || '');
        /** Contains all the nodes */
        var nodes = {};

        /**
         * This could be refactored into a nice angular service, but for the sake of
         * simplicity, we put it all in here
         */
        //clusterNodesWS.onmessage = function(msg) {
        //    var node = JSON.parse(msg.data);
        //    if (node.state == 'removed') {
        //        delete nodes[node.address];
        //    } else {
        //        nodes[node.address] = node;
        //    }
        //    $scope.$apply(function() {
        //        $scope.nodes = _.values(nodes);
        //    });
        //};

        // clusterMetricsWebsocket
        //var clusterMetricsSocketUrl = playRoutes.controllers.Cluster.clusterMetricsWebsocket().webSocketUrl();
        //var clusterMetricsWS = new WebSocket(clusterMetricsSocketUrl);

        //clusterMetricsWS.onmessage = function(msg) {
        //    var metric = JSON.parse(msg.data);
        //
        //    $scope.$apply(function() {
        //        var node = nodes[metric.address.full];
        //        if (node === undefined) {
        //            // node not available
        //        } else if (metric.name == 'heap') {
        //            node.heap = (metric.used / (1024 * 1024)).toFixed(2) + 'Mb';
        //            node.heapUsed = metric.used;
        //        } else if (metric.name == 'cpu') {
        //            if (metric.cpuCombined) {
        //                node.cpu = (metric.cpuCombined * 100).toFixed(2);
        //                node.cpuCombined = metric.cpuCombined;
        //            }
        //            node.systemLoadAverage = metric.systemLoadAverage;
        //        }
        //
        //        // Setting metrics
        //        $scope.metrics = _.values(nodes).map(function(node) {
        //            return {
        //                name : node.host + ':' + node.port,
        //                value : (node.heapUsed || 0)
        //            };
        //        });
        //
        //    });
        //};
    };
    DashboardCtrl.$inject = [ '$scope', '$http' ];

    return {
        DashboardCtrl : DashboardCtrl
    };

});
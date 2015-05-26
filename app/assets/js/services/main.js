/**
 * Created by m.cherkasov on 15.05.15.
 */


define(['angular'], function(angular) {
    'use strict';

    return angular.module('rema7.services', []).factory('rpcService',function(){
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
    });
});
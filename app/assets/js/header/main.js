/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.header', ['ui.router', 'ui.bootstrap', 'header.routes', 'rema7.services']);
    mod.controller("HeaderCtrl", controllers.HeaderCtrl);

    return mod;
});
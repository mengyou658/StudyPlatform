/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.home', ['ngRoute', 'home.routes']);
    mod.controller("HeaderCtrl", controllers.HeaderCtrl);

    return mod;
});
/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.home.sets', ['ui.router', 'home.sets.routes']);
    mod.controller("CardsSetCtrl", controllers.CardsSetCtrl);

    return mod;
});
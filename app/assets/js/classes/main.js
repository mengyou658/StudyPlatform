/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.classes', ['ui.router', 'classes.routes', 'ngAnimate']);

    mod.controller("ClassesCtrl", controllers.ClassesCtrl);

    return mod;
});
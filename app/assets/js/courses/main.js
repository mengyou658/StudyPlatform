/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.courses', ['ui.router', 'courses.routes', 'ngAnimate']);

    mod.controller("CoursesCtrl", controllers.CoursesCtrl);

    return mod;
});
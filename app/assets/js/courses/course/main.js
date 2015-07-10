/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.courses.course', ['ui.router', 'courses.course.routes', 'ngAnimate']);

    mod.controller("CourseCtrl", controllers.CourseCtrl);

    return mod;
});
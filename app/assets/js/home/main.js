/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.home', ['ui.router', 'home.routes', 'xeditable', 'ngAnimate']);
    mod.run(function(editableOptions) {
        editableOptions.theme = 'bs3';
    });
    mod.controller("HeaderCtrl", controllers.HeaderCtrl);

    return mod;
});
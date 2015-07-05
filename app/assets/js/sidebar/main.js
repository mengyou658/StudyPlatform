/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', './controllers'], function(angular, routes, controllers) {
    'use strict';

    var mod = angular.module('rema7.sidebar', ['ui.router', 'ui.bootstrap', 'sidebar.routes', 'rema7.services']);
    mod.controller("SidebarCtrl", controllers.SidebarCtrl);

    return mod;
});
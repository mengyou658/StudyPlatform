/**
 * Created by maximcherkasov on 10.05.15.
 */


define(['angular', './routes'], function(angular) {
    'use strict';

    return angular.module('rema7.settings.products', ['ui.router',
        'settings.products.routes', 'ui.bootstrap']);
});
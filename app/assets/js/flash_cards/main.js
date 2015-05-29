/**
 * Created by maximcherkasov on 09.05.15.
 */

define(['angular', './routes', 'datatables', 'angular-datatables'], function(angular) {
    'use strict';

    return angular.module('rema7.flashCards', ['ui.router', 'flashCards.routes', 'datatables']);
});
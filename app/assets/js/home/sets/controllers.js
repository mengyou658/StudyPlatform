/**
 * Created by maximcherkasov on 09.05.15.
 */
define([], function() {
    'use strict';

    var CardsSetCtrl = function($scope, $http, $state, $stateParams, modalService) {
        $scope.set = {};
        $scope.query = {};
        $scope.cards = [];

        $http.get('/sets/'+$stateParams.setId) .success(function(data) {
            $scope.set = data;
            $http.get('/sets/'+$stateParams.setId+'/cards').success(function(data) {
                $scope.cards = data;
            });
        });

        $scope.save = function(data, id) {
            angular.extend(data, {id: parseInt(id)});

            return $http.post('/sets/'+$stateParams.setId, data);
        };

        $scope.searchCard = function (row) {
            if (!$scope.query.text) return true;

            console.log(row);
            console.log(row.term);
            console.log(angular.lowercase(row.term));

            return (angular.lowercase(row.term).indexOf($scope.query.text || '') !== -1
                    || angular.lowercase(row.transcription).indexOf($scope.query.text || '') !== -1
                    || angular.lowercase(row.definition).indexOf($scope.query.text || '') !== -1);
        };

        var initModal = function() {
            modalService.initAsCardsSet({
                templateUrl: '/assets/partials/modals/manageCardsSet.html',
                windowClass: 'cards-set-modal'
            });
        };

        initModal();

        $scope.editCardSet = function(){
            initModal();
            modalService.edit($scope.set).result.then(function (data) {
                $scope.set.name = data.name;
                $scope.set.updated = data.updated;

            });
        };


    };
    CardsSetCtrl.$inject = [ '$scope', '$http', '$state', '$stateParams', 'modalService' ];


    var CardsSetEditCtrl = function($scope, $http, $stateParams) {
        $scope.set = {};
        $scope.newCard = {};

        console.log($stateParams.setId);

        $http.get('/sets/'+$stateParams.setId).success(function(data) {
            $scope.set = data;
            $http.get('/sets/'+$stateParams.setId+'/cards').success(function(data) {
                $scope.set.cards = data;
            });
        });

        $scope.saveRow = function(data) {
           $http.post('/sets/'+$stateParams.setId, {
               id: parseInt(data.id),
               term: data.term,
               transcription: data.transcription,
               definition: data.definition
           });
        };

        $scope.addRow = function() {
            var card = $scope.newCard;
            if (card.term && card.definition) {
                $http.post('/sets/'+$stateParams.setId, card).success(function(data) {
                    console.log(data);
                    $scope.newCard = {};

                    $scope.set.cards.push(data);
                }).error(function(data, status, headers, config) {
                    console.log(data);
                });
            }
        };

        $scope.removeRow = function(card) {
            $http.delete('/sets/'+$stateParams.setId + '/cards/'+card.id).success(function(data) {
                console.log(data);
                var index = $scope.set.cards.indexOf(card);

                if (index > -1) {
                    $scope.set.cards.splice(index, 1);
                }
            }).error(function(data, status, headers, config) {
                console.log(data);
            });
        };

        $scope.getTerms = function(val) {
            return $http.get('/dict/'+val).then(function(response){
                console.log(response.data);
                return response.data
            });
        };

        $scope.selectTerm = function(item, model, label) {
            $scope.newCard.term = model.simplified;
            $scope.newCard.transcription = model.pinyin;
            console.log(item);
            console.log(model);
            console.log(label)
        }
    };
    CardsSetEditCtrl.$inject = [ '$scope', '$http', '$stateParams' ];

    return {
        CardsSetCtrl : CardsSetCtrl,
        CardsSetEditCtrl : CardsSetEditCtrl
    };

});
(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestproductDetailController', TestproductDetailController);

    TestproductDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Testproduct'];

    function TestproductDetailController($scope, $rootScope, $stateParams, previousState, entity, Testproduct) {
        var vm = this;

        vm.testproduct = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:testproductUpdate', function(event, result) {
            vm.testproduct = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

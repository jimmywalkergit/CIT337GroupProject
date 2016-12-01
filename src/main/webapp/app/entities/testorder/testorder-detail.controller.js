(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestorderDetailController', TestorderDetailController);

    TestorderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Testorder', 'Testproduct'];

    function TestorderDetailController($scope, $rootScope, $stateParams, previousState, entity, Testorder, Testproduct) {
        var vm = this;

        vm.testorder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:testorderUpdate', function(event, result) {
            vm.testorder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

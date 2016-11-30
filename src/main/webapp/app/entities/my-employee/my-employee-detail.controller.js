(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyEmployeeDetailController', MyEmployeeDetailController);

    MyEmployeeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyEmployee'];

    function MyEmployeeDetailController($scope, $rootScope, $stateParams, previousState, entity, MyEmployee) {
        var vm = this;

        vm.myEmployee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cit337ProjectApp:myEmployeeUpdate', function(event, result) {
            vm.myEmployee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

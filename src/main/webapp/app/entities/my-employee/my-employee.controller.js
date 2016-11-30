(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyEmployeeController', MyEmployeeController);

    MyEmployeeController.$inject = ['$scope', '$state', 'MyEmployee'];

    function MyEmployeeController ($scope, $state, MyEmployee) {
        var vm = this;
        
        vm.myEmployees = [];

        loadAll();

        function loadAll() {
            MyEmployee.query(function(result) {
                vm.myEmployees = result;
            });
        }
    }
})();

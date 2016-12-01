(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestorderController', TestorderController);

    TestorderController.$inject = ['$scope', '$state', 'Testorder'];

    function TestorderController ($scope, $state, Testorder) {
        var vm = this;
        
        vm.testorders = [];

        loadAll();

        function loadAll() {
            Testorder.query(function(result) {
                vm.testorders = result;
            });
        }
    }
})();

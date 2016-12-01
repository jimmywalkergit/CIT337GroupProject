(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('TestorderDeleteController',TestorderDeleteController);

    TestorderDeleteController.$inject = ['$uibModalInstance', 'entity', 'Testorder'];

    function TestorderDeleteController($uibModalInstance, entity, Testorder) {
        var vm = this;

        vm.testorder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Testorder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

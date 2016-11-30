(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyEmployeeDeleteController',MyEmployeeDeleteController);

    MyEmployeeDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyEmployee'];

    function MyEmployeeDeleteController($uibModalInstance, entity, MyEmployee) {
        var vm = this;

        vm.myEmployee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyEmployee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

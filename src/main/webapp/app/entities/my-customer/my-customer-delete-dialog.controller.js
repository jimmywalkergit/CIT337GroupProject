(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCustomerDeleteController',MyCustomerDeleteController);

    MyCustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyCustomer'];

    function MyCustomerDeleteController($uibModalInstance, entity, MyCustomer) {
        var vm = this;

        vm.myCustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyCustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

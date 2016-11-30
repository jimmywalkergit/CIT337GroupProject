(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyProductDeleteController',MyProductDeleteController);

    MyProductDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyProduct'];

    function MyProductDeleteController($uibModalInstance, entity, MyProduct) {
        var vm = this;

        vm.myProduct = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyProduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

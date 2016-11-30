(function() {
    'use strict';

    angular
        .module('cit337ProjectApp')
        .controller('MyCartDialogController', MyCartDialogController);

    MyCartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyCart', 'MyProduct'];

    function MyCartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyCart, MyProduct) {
        var vm = this;

        vm.myCart = entity;
        vm.clear = clear;
        vm.save = save;
        vm.myproducts = MyProduct.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myCart.id !== null) {
                MyCart.update(vm.myCart, onSaveSuccess, onSaveError);
            } else {
                MyCart.save(vm.myCart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cit337ProjectApp:myCartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

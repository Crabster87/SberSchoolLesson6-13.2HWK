# SberSchoolLesson6HWK & SberSchoolLesson13/2HWK
-----------------------------------------------------------------------------------------------------------------------------------------------------
## Лекция 5. Адаптеры.

### Реализовать работу с RecyclerView.

* Список должен состоять из нескольких типов элементов - «Корзина», «Яблоко» и «Сумма». 

* На каждом элементе «Корзина», помимо текста, должна быть кнопка «Положить яблоко». 
По нажатию на кнопку под текущим элементом «Корзины» должен добавиться элемент - «Яблоко».

* «Яблоко» содержит текст и кнопку «Съесть». Нажатие на кнопку «Съесть» удаляет это яблоко 
из этой корзины. В каждой корзине может быть не больше 3 яблок, при попытке добавить 4е 
яблоко должно выводиться сообщение и вреде жадности.

* Элемент «Сумма» должен быть последним элементов в списке и содержать текстовое сообщение 
с общим количеством яблок во всех корзинах в данный момент.

* Под/над списком должны быть кнопки «Добавить корзину» и «Удалить все корзины».

#### Желательно добавить один из элементов кастомизации отображения RecyclerView на выбор, 
#### например - DiffUtil/ListAdapter, ItemDecorator, ItemAnimator, LinearSnapHelper
-----------------------------------------------------------------------------------------------------------------------------------------------------
## Лекция 13. Анимации в Android.

### Добавить новый функционал в приложение из Лекции 5. 

* Перемещение яблок между корзинами. Корзины двигать нельзя. Когда яблоко перемещается, 
то оно увеличивается в размере на 10% и становится прозрачнее на 10% 

* Свайп вправо – яблоко удаляется. При свайпе яблоко не меняет свой размер

* Свайп корзины влево – удаляется корзина с яблоками

* Если в корзине не помещается яблоко, то выводим Toast, что корзина переполнена и 
возвращаем яблоко на место
-----------------------------------------------------------------------------------------------------------------------------------------------------
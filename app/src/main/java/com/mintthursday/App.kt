package com.mintthursday

import android.app.Application
import androidx.room.Room
import com.mintthursday.database.AppDatabase
import com.mintthursday.models.Ingredient
import com.mintthursday.models.Recipe
import com.mintthursday.models.Step

class App : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries() //todo
            .fallbackToDestructiveMigration() //todo
            .build()
        with(database.recipeDao()) {
            if (database.recipeDao().getAllRecipes().isEmpty()) {
                insertRecipe(
                    Recipe(
                        id = 0,
                        name = "Медовые кексы с яблоками",
                        description = "Кексы или, как их еще называют, маффины – очень простой в приготовлении десерт, с которым справится даже подросток или начинающая хозяйка без опыта. Более того, в этот процесс можно вовлекать маленьких детей, которым будет интересно помочь взрослым. Поэтому если вы уже пробовали делать летом кексы со свежей малиной, приготовьте на этот раз медовые кексы с яблоками.",
                        countPortion = 12,
                        time = 60,
                        category = "Десерт",
                        ingredients = listOf(
                            Ingredient("Яблоко", 3.0, "шт"),
                            Ingredient("Жидкий мед", 100.0, "г"),
                            Ingredient("Масло сливочное", 100.0, "г"),
                            Ingredient("Яйцо", 2.0, "шт"),
                            Ingredient("Мука", 170.0, "г"),
                            Ingredient("Разрыхлитель", 0.5, "ч.л."),
                            Ingredient("Соль", 1.0, "щеп"),
                        ),
                        steps = listOf(
                            Step("Яблоки промываем в проточной воде, чистим от кожуры, удаляем сердцевину. Нарезаем маленькими кубиками. Орехи кешью рубим ножом в мелкую крошку. Сливочное масло заранее выставляем из холодильника, чтобы оно растаяло при комнатной температуре."),
                            Step("В глубокую миску разбиваем яйца, добавляем жидкий мед и щепотку соли. Берем миксер и взбиваем все вместе на высокой скорости около 1 минуты – до образования пены. Затем добавляем мягкое сливочное масло и взбиваем еще 1-2 минуты."),
                            Step("В тесто добавляем просеянную через сито муку с разрыхлителем. Перемешиваем и взбиваем на низкой скорости до однородности теста. Затем добавляем туда нарезанные яблоки."),
                            Step("Берем формы для кексов – силиконовые или бумажные, раскладываем их на большой противень. Выкладываем в каждую форму порцию теста. Заранее разогреваем духовку до 180°С. Ставим противень в горячую духовку и выпекаем около 20-25 минут – в зависимости от мощности."),
                            Step("Вытаскиваем кексы из формочек и ждем, пока они остынут."),
                            Step("Подаем аппетитные кексы к чаю или какао и наслаждаемся вкусом."),
                        ),
                        link = "https://apostrophe.ua/article/lime/lifestyle/2021-09-26/pechem-doma-aromatnyie-medovyie-keksyi-s-yablokami-poshagovyiy-retsept/41960"
                    ),
                    Recipe(
                        id = 0,
                        name = "Лаваш с творогом и яблоками",
                        description = "Вкуснейший рецепт из лаваша с творогом и яблоками в карамельном соусе. Рецепт очень простой в приготовлении. Я использовала плотные кисло-сладкие яблоки. Но использовать можно любые яблоки, регулируя при этом количество сахара в большую или меньшую сторону. Дополнительно я использовала лимонный сок, который делает вкус яблочной начинки более выразительным, а также лимонную цедру, которая делает вкус и аромат десерта более изысканным. Получилось очень вкусно, а главное быстро и красиво!",
                        countPortion = 1,
                        time = 120,
                        category = "Десерт",
                        ingredients = listOf(
                            Ingredient("Лаваш", 200.0, "г"),
                            Ingredient("Творог", 400.0, "г"),
                            Ingredient("Сахар", 50.0, "г"),
                            Ingredient("Яйцо", 1.0, "шт"),
                            Ingredient("Ванильный сахар", 1.0, "ч.л."),
                            Ingredient("Яблоко", 3.0, "шт"),
                            Ingredient("Сахар", 50.0, "г"),
                            Ingredient("Масло сливочное", 50.0, "г"),
                            Ingredient("Цедра половины лимона", 1.0, "шт"),
                            Ingredient("Сок половины лимона", 1.0, "шт"),
                            Ingredient("Соль", 1.0, "щеп"),
                        ),
                        steps = listOf(
                            Step("Яблоки почистить, порезать очень мелкими кубиками. Добавить сок и цедру половины лимона. Перемешать."),
                            Step("Готовим карамель. На сковороде растопить сливочное масло, высыпать равномерным слоем сахар."),
                            Step("На среднем нагреве довести сахар до светлого коричневого цвета."),
                            Step("Добавить яблоки, перемешать."),
                            Step("Тушить на небольшом нагреве под крышкой примерно 5-7 минут до полуготовности. У меня были очень плотные яблоки, поэтому я их тушила. Если же у вас яблоки мягкие, десертного сорта, то можно их не тушить, перемешать, и далее по рецепту."),
                            Step("Приготовим творожную начинку. Творог, сахар, яйцо и ванильный сахар тщательно измельчить блендером. Необходимо чтобы получилась гладкая творожная масса, довольно пластичная, чтобы ее можно было размазать по лавашу, но при этом не слишком жидкая. Консистенция готовой творожной массы зависит от того, какой у вас творог, зернистый по типу домашнего или мягкий пастообразный."),
                            Step("Лаваш развернуть (у меня два листа лаваша каждый по 100 г), на фото один лист лаваша. Равномерно намазать половину творожной начинки."),
                            Step("Закрутить в плотный рулет, начиная с короткой стороны."),
                            Step("Порезать на кусочки. То же самое проделать со вторым лавашем. Всего у меня получилось 14 рулетиков из лаваша."),
                            Step("В форму переложить яблоки вместе с карамелью, разровнять. Форма должна быть небольшая по площади, необходимо чтобы рулеты стояли плотно друг к другу."),
                            Step("Сверху поставить рулеты из лаваша с творогом, слегка прижать их вглубь."),
                            Step("Поставить в разогретую до 180 градусов духовку, выпекать 20-30 минут."),
                            Step("Готовое блюдо вынуть из духовки, приложить сверху большую тарелку, и перевернуть. Если на дне формы после выпечки осталась жидкая карамель, которая еще не впиталась в лаваш, то будьте осторожны при переворачивании, она может вылиться по краям."),
                            Step("Подавать лаваш с творогом и яблоками можно как теплым так, и полностью остывшим. Вкусно и так, и так. В теплом виде более выражен творожный вкус, а в остывшем виде на первый план выходит кисло-сладкий вкус яблочной верхушки. Лучше всего попробуйте и в теплом, и в остывшем виде. чтобы определить как вам вкуснее. Лаваш с творогом и яблоками это красивое и вкусное блюдо для всей семьи!"),
                        ),
                        link = "https://kamelena.ru/recipe/Lavash-s-tvorogom-i-yablokami"
                    ),
                    Recipe(
                        id = 0,
                        name = "Тыквенный кекс",
                        description = "Очень интересный кекс, готовится на основе тыквенного пюре. Как я уже раньше писала, характерный вкус тыквы я не люблю, но в этом тыквенном кексе все по-другому. Благодаря корице и цедре лимона, тыквы не чувствуется совершенно. Кекс получается с влажной, нежной структурой, очень мягкий, немного похож на вот эти Морковные кексы, такой же ароматный и очень вкусный. Вместо цедры лимона можно использовать цедру апельсина, также по желанию можно добавить мускатный орех, горсть изюма или орехов. Я дополнительно полила кекс глазурью на основе какао, чтобы подчеркнуть пряный вкус кекса. Хотя и без глазури этот кекс очень и очень хорош. Так что, если вам не хочется делать глазурь, вы можете просто посыпать кекс сахарной пудрой, получится все равно очень вкусно.",
                        countPortion = 1,
                        time = 90,
                        category = "Десерт",
                        ingredients = listOf(
                            Ingredient("Тыквенное пюре", 250.0, "г"),
                            Ingredient("Мука", 250.0, "г"),
                            Ingredient("Сахар", 150.0, "г"),
                            Ingredient("Растительное масло", 50.0, "мл"),
                            Ingredient("Яйцо", 2.0, "шт"),
                            Ingredient("Разрыхлитель", 2.0, "ч.л."),
                            Ingredient("Корица", 1.0, "ч.л."),
                            Ingredient("Цедра лимона", 1.0, "шт"),
                            Ingredient("Соль", 1.0, "щеп"),
                        ),
                        steps = listOf(
                            Step("Тыкву почистить, порезать крупными кусочками."),
                            Step("Запечь в микроволновке до полной мягкости, примерно 5-10 минут (если выделилась жидкость, ее необходимо слить). Если нет микроволновки, можно запечь в духовке. Или же залить небольшим количеством воды в кастрюльке, и протушить до полной мягкости, затем откинуть на дуршлаг, чтобы вся жидкость стекла. Я запекаю в микроволновке, это самый быстрый и легкий способ. Пюрировать тыкву с помощью блендера, слегка остудить."),
                            Step("С лимона снять цедру, путем натирания на мелкой терке, не затрагивая белый слой. Добавить к тыквенному пюре сахар, масло, яйца, цедру, соль. Если у вас сладкая тыква, количество сахара можно уменьшить."),
                            Step("Немного взбить миксером или ручным венчиком."),
                            Step("Добавить муку, смешанную с корицей и разрыхлителем. Еще немного взбить до однородности. Тесто получается не густое."),
                            Step("Перелить в форму, смазанную маслом (моя форма размером 24x10 см). Поставить в разогретую до 180 градусов духовку, выпекать примерно 40-60 минут или до «сухой спички»."),
                            Step("Готовый кекс остудить."),
                        ),
                        link = "https://kamelena.ru/recipe/Tykvennyj-keks"
                    )
                )
            }
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
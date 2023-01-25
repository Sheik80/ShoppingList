Здесь описываем что зачем и почему
Нам нужно несколько таблиц куда юзер будет запишивать список покупок
lesson 2-3 создаем таблицы БД
Вторая таблица для самих продуктов
Entity это модель БД
С помощью либы Room мы создаем аннотацию и таблицы нашей БД
entities хранит все модели наших таблиц
В моелях создаем dataClasses
@Entity(tableName = "shopping_list_names") Это таблица и модель только для записи Имен списков, например на рыбалку, новый год и т.д.
@ это аннотация для Room Lib
@Serializable это передача класса в другие классы, мы можем передавать из активити в активити весь класс, а не отдельные переменные
@Entity(tableName = "shop_list_item") Это таблица и модель для элемента закупки(картошка, моркошка или крючки, леска, грузила)
У данной таблице тоже должен быть id чтобы обратиться к этому списку у поменять что-то у элемента
@Entity(tableName = "note_list")
Это таблица для хранения записей
@Entity(tableName = "library")
Это таблица для описания элементов списка (яблоко, и т.д.) мы ее будем наполнять
В БД мы через ROOM будем пилить эти Таблички
lesson 4
class MainDataBase это главный класс или наша БД, наследуем от билблиотеки ROOM и получаем БД
указываем companion object тогда мы можем обращаться к БД через точку например MainDataBase.функция
и наш класс должен быть abstract
@Volatile дает доступность к БД всех потоков
private var INSTANCE:MainDataBase? = null это экземпляр нашего класса, благодаря Volatile мы имеем достпу к INSTANCE
return INSTANCE ?: synchronized(this)  здесь мы возвращаем нашу БД оператор ?: если слева есть экземпляр нашей БД то вернем т.к. у нас будет уже БД, если нет то через synchronized
он читает поток если уже используется то не может запустить создание БД, а если поток свободен
через Room.databaseBuilder создаем БД именно текущего экземпляра this, он читает поток если уже используется то не может запустить создание БД
Указываем контекст, укажем AppContext чтобы всего активи взять и обращаться отовсюду к БД
Здесь указываем Базу и какие у нее есть таблицы, и так же версию
@Database(entities = [LibraryItem::class, NoteItem::class, ShoppingListItem::class, ShoppingListNames::class], version = 1)
class MainApp : Application() Создаем класс МайнАпп и в данном главном активити мы инициализируем нашу БД с доступом из всех активити
val database by lazy {
MainDataBase.getDataBase(this)
Делаем ленивую инициализацию БД, т.е. когда есть обращение и если у нас БД не существует, создает. А если есть БД то by lazy не будет работать.
НО правильно ли так делать, мы можем объявить БД в во ViewModel
lesson 5
interface Dao
class Dao или Дата Акцесс Объект. Класс DAO нужен чтобы обращаться с БД, записывать или считывать инфу
фунция записи Insert в БД у нас должна быть suspend чтобы использовать ее с кортинами
suspend fun insertNote(note: NoteItem) передаем нашу таблицу для заметок
@Query("SELECT * FROM note_list") здесь запрос и нужен синтаксис SQLite, тут можем выбирать по слову, букве и т.д. и используемм Flow fun getAllNotes() : Flow<List<NoteItem>>
Flow спец класс в корутинах, который подключает БД к списку и автоматом обновляет список, но не всегда нам нужен Flow. Например когда нам нужно только один раз обратиться к БД.
abstract fun getDao(): Dao Эта функция в MainDataBase возвращает интерфей и мы можем брать инстанс базы который делаем в MainApp и можем обращаться через точку database.Нужная БД
lesson 6
Удаляем ночную тему
Добавляем меню через BottomNavigationView
Прикрепляем справа, снизу, слева, 
Добавляем в ресурсы menu, создать new, android resource directory, а в нем создаем меню bottom_menu
там в xml уже пилим кнопки, ресурсы пишем в strings
Затем в drawable добавляем иконки из стандартного материал дизайна
иконки прописываем в xml bottom_menu
Lesson 7
Готовим фрагменты,
abstract class BaseFragment: Fragment() Основной фрагмент с одной функцией и мы будем от него наследоваться
используем полиморфизм
object FragmentManager используем object чтобы юзать функцию без инициализации нашего класса
var currentFrag: BaseFragment? = null
когда в активити будем передавать фрагмент мы будем обращаться FragmentManager.currentFrag 
и буем обращаться к текущему фрагменту
fun setFragment() передаем сюда новый фрагмент и контекст
val transaction = activity.supportFragmentManager.beginTransaction() это стандартный метод из активити,
где мы можем удалять, заменять фрагмент из транзакции
Далее запиливаем наш FrameLayot в разметку чтобы знать куда помещать фрагмент из транзакции
currentFrag = newFrag заменяем на фрагмент который у нас будет, например для списка с покупками или другого списка
Lesson 8
Создаем список RecyclerView, адаптер берет заполняет и печатает
ListAdapter имполтируем от recyclerview widget
NoteAdapter наследуем от ListAdapter и  с помощью DiffUtil будем загонять туда элементы
В класс передаем наш же созданный NoteAdapter.ItemHolder с нашей разметкой
note_list_item это наша разметка для элемента в списке
создаем разметку с помощью cardview, linear and constraint layouts
в NoteAdapter будем передавать наш лист из NoteItem, вернее формирвоать список и ViewHoldder
Создаем внутри еще один класс ItemHolder c элементами view, там может быть хоть 100500 элементов
ItemHolder наследуем от RecyclerViewGolder и там создаем функцию 
setDAta в нее пилим переменную Note от NoteItem
binding используем для получения разметки
fun setData(note: NoteItem) = with(binding) позволяет в  теле функции обращаться к элементам
tvTitle.text = note.title обращение через переменную note в функции к нашему классу
companion object{
fun create(parent: ViewGroup) : ItemHolder {
Через  companion object мы инициализуем наш Holder 
а далее через layoutInflater мы передаем данные в нашу разметку или надуваем ИНФЛЭЙТИМ
LayoutInflater.from(parent.context).inflate(R.layout.note_list_item
мы передаем контекст а у activity нет контекста, поэтому parent наследуем от ViewGroup
а возвращать будем инициализированный класс ItemHolder с разметкой
class NoteAdapter: ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemCorporator())
class ItemCorporator : DiffUtil.ItemCallback<NoteItem>() Этот созданный класс наследуется от DiffUtil 
чтобы он сам проверял дублирование элементов в списке поэтому сравниваем по id
Вторая функция сравнивает вообще весь контент
Дальше инмлементим фунции Holdera
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
return ItemHolder.create(parent) создаем холдер и возвращаем для каждой заметки свой Холдер и разметкой для каждого элемента
override fun onBindViewHolder(holder: ItemHolder, position: Int) {
holder.setData(getItem(position))
А вот здесь мы заполняем наш холдер, из БД по позиции, getItem забирает все элементы в списке.

LESSON_9
Делаем фрагмент для заметок
NoteFragment, сразу создает с разметкой.
Удаляем  все не нужное.
Добавляем байдинг
binding = FragmentNoteBinding.inflate(inflater, container,false) инфлэйтер приходит из onCreateView
companion object {
@JvmStatic
fun newInstance() = NoteFragment() создает инстанцию, если она есть то выдает ту что есть
class NoteFragment : BaseFragment() наследуемся от BaseFragment значит имплементируем функцию из BaseFragment
onClickNew
В MainActivity вешаем на нажатие кнопки, создаем новую инстанцию нашего фрагмента
R.id.notes->{
FragmentManager.setFragment(NoteFragment.newInstance(),this)
this а данном случае AppCompatActivity он содержит наш контент
}
Lesson10
Учим MVVM
создаем классы моделей
class MainViewModel(dataBase: MainDataBase): ViewModel() наследуем from ViewModel в аргументы передаем нашу БД
Потом получаем DAO с нашей модели
Так же закидываем LiveData список из наших NoteItem, через обсервер будем слудить за обновлениями за БД
т.к. у нас flow пишем что это as LiveData
fun insertNote(note:NoteItem) = viewModelScope.launch {
dao.insertNote(note)
таким образом мы не обращаемся во вью, а используем скоуп корутину и вставляем запись в БД
MainViewModel мы инициализируем через MainViewModelFactory наследуем через ViewModelProvaider.Factory
в него передаем нашу БД это рекомендашка от андроид
и возврашаем модель с БД as T Это ДЖЕНЕРИК
И НУЖНО ВЫКИНУТЬ эКСЕПШН
в NoteFragment создаем переменную
private val mainViewModel: MainViewModel by activityViewModels {
и передаем нашу БД, но напрямую ее нет, мы базу получаем из нашего MainApp в котором мы проинициализировали БД
MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
и теперь мы можем к нашей переменной mainViewModel обращаться
Дальше можно подключить обсервер и он будет слдедить за изменениями.
 
Lesson 11
Создаем новое активити NewNoteActivity с EmptyActivity и делаем разметку с помощью ConstrainLayout
сразу подклчаем binding
в разметке в стригах прописываем hint для title & description
Нужно добавить две кнопки сохранить и выйти обратно, это делаем через меню
создаем new_note_menu
Добавляем иконку через dravable->new->vectorAsset-> именуем ic_save
Чтобы подключить меню, заходим в активити и пишем код
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
menuInflater.inflate(R.menu.new_note_menu, menu)
return super.onCreateOptionsMenu(menu)
И ДОБАВЛЯЕМ СЛУШАТЕЛЬ нажатий на нашем меню
override fun onOptionsItemSelected(item: MenuItem): Boolean
item.itemId == R.id.id_save значит сохраняем, другая кнопка выходим
finish() закрывает активити
в нашем NoteFragment чтобы вызвать другое активити запускаем вызов по кнопке onClickNew
override fun onClickNew() {
startActivity(Intent(activity, NewNoteActivity::class.java))
Каждый раз когда мы показываем какой-либо фрагмент мы его пишем в нам FragmentManager в currentfragment
Но т.к. наши фаргменты наследуются от базового фрагмента baseFragment Manager сам понимает какой фрагмент взять
и на нем запустить наш onClickNew
FragmentManager.currentFrag?.onClickNew() from MainActivity запустит наш фрагмент тот который нужен со своим Активити
private fun actionBarSettings(){
val ab = supportActionBar
ab?.setDisplayHomeAsUpEnabled(true)
позволяет показывать нашу кнопку home в активити
вызываем его в onCreate
WARNING Манифест не видел активити, при добавлении нужно прописать пакет где лежит
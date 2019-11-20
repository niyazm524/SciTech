class MyDatabase : Database() {
    override val url =
        "jdbc:mysql://localhost/scitech?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=Europe/Moscow"
    override val username = "root"
    override val password = "roulette"

}
class MyDatabase : Database() {
    override val url = "jdbc:mysql://localhost/scitech?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC"
    override val username = "root"
    override val password = "roulette"

}
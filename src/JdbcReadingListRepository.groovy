@Repository
class JdbcReadingListRepository implements ReadingListRepository{

    //CLI知道大多数常用的类所在的包，当编译失败时会自动触发下载这些包，并把依赖加入classpath中
    @Autowired
    JdbcTemplate jdbcTemplate

    List<Book> findByReader(String reader) {
        jdbcTemplate.query(
                "select id, reader, isbn, title, author, description " +
                        "from Book where reader=?",
                { rs, row ->
                    new Book(id: rs.getLong(1),
                            reader: rs.getString(2),
                            isbn: rs.getString(3),
                            title: rs.getString(4),
                            author: rs.getString(5),
                            description: rs.getString(6))
                } as RowMapper,
                reader)
    }

    void save(Book book) {
        jdbcTemplate.update("insert into Book " +
                "(reader, isbn, title, author, description) " +
                "values (?, ?, ?, ?, ?)",
                book.reader,
                book.isbn,
                book.title,
                book.author,
                book.description)
    }
}

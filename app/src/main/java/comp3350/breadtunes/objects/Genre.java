package comp3350.breadtunes.objects;

class Genre {
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

class UnknownGenre extends Genre {
    public UnknownGenre() {
        super("Unknown");
    }
}

package ru.egor_d.instagramcollage.model;

/**
 * Created by Egor Danilin on 15.01.2015.
 */
public class InstagramResponse<T> {
    public InstagramMeta meta;
    public InstagramPagination pagination;
    public T data;
}

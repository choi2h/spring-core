package hello.advanced.trace;

import java.util.UUID;

public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        //생성된 UUID의 8자리만 사융
        return UUID.randomUUID().toString().substring(0,8);
    }

    //깊이를 표현하기 위한 level 증가
    public TraceId createNextId() {
        return new TraceId(id, level+1);
    }

    //깊이를 표현하기 위한 level 감소
    public TraceId createPreviousId() {
        return new TraceId(id, level-1);
    }

    public boolean isFirstLever() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}

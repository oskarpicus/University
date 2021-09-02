$(function (){
    $("th").click(function (){
        let tds = $(this).siblings("td").toArray();
        tds.sort((td1, td2) => {
            let t1 = $(td1), t2 = $(td2);
            if (t1.html() === t2.html())
                return 0;
            if(!isNaN(t1.html()) && !isNaN(t2.html()))
                return Number(t1.html()) < Number(t2.html()) ? -1 : 1;
            return t1.html() < t2.html() ? -1 : 1;
        })

        for(let i = 0; i < tds.length ; i++){
            let wrapper = $(tds[i]);
            let index = wrapper.index();
            wrapper.parents("table").first()
                .find("tr")
                .each(function (currentIndex, currentTr){
                   let tr = $(currentTr);
                   let child = tr.children("td").eq(index - 1);
                   tr.append(child);
                });
        }
    });
})
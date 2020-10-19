% ---------- super class ----------
super(laptop, accessories).
super(accessories, mouse).
super(accessories, laptop).
super(accessories, headphones).
super(accessories, flashDrive).

:- discontiguous
    prop/3,
    write/1.
% ------- end of super class ------


% ---------- same as ---------- 
sameas(X, X). % itself is same as itself
sameas(you, chatbot).
sameas(is, are). % synonymous
% ------- end of same as ------- 


% ----------- databases ----------
prop(mouse, type, accessories).
prop(mouse, brand, "Logitech M185 Wireless").
prop("Logitech M185 Wireless", price, "$14.99").
prop(mouse, brand, "Apple Magic Mouse").
prop("Apple Magic Mouse", price, "$95.00").
prop(mouse, brand, "Microsoft Bluetooth Mouse").
prop("Microsoft Bluetooth Mouse", price, "$29.99").

prop(keyboard, type, accessories).
prop(keyboard, brand, "Asus TUF Gaming K7").
prop("Asus TUF Gaming K7", price, "$197.99").
prop(keyboard, brand, "HP USB Collaboration Key").
prop("HP USB Collaboration Key", price, "$79.99").
prop(keyboard, brand, "ACER Aspire One Series 521").
prop("ACER Aspire One Series 521", price, "$84.99").

prop(headphones, type, accessories).
prop(headphones, brand, "Sony XB700 On-Ear Bluetooth Headphones").
prop("Sony XB700 On-Ear Bluetooth Headphones", price, "$129.99").
prop(headphones, brand, "Apple AirPods In-Ear").
prop("Apple AirPods In-Ear", price, "$219.99").
prop(headphones, brand, "Marshall Monitor Over-Ear Bluetooth Headphones").
prop("Marshall Monitor Over-Ear Bluetooth Headphones", price, "$149.99").

prop(flashDrive, type, accessories).
prop(flashDrive, brand, "Seagate Expansion 1TB 5400RPM").
prop("Seagate Expansion 1TB 5400RPM", price, "$74.99").
prop(flashDrive, brand, "Samsung 860 EVO 500GB SATA").
prop("Samsung 860 EVO 500GB SATA", price, "$94.99").
prop(flashDrive, brand, "Lenovo Drive Bay Adapter Internal").
prop("Lenovo Drive Bay Adapter Internal", price, "$32.61").
% -------- end of databases -------


% ---------- self awareness ----------
prop(chatbot, identity, "Swaccessories Shop Chatbot").
% ------- end of self awareness -------


% ---------- linguistic ----------
isgreeting(hello).
isgreeting(hi).
isgreeting(hey).
isgreeting([H | _]) :- isgreeting(H).
isgreeting([_ | T]) :- isgreeting(T).
isleaving(goodbye) :- write("Goodbye."), nl.
% ------- end of linguistic -------


% ---------- is what ------------
istype(O, T) :- prop(O, type, T).
isbrand(O, T) :- prop(T, brand, O).
% ------- end of is what --------


% ---------- user stating -----------
state(G):- 
    (   
    	(   isgreeting(G), start	) ->  true;
    	(   isleaving(G)	) ->  true
    ).
% ------- end of user stating --------


% ---------- phrases parses ------------
noun(N, [N]).
noun(N, [the | N1]) :- noun(N, N1).
noun(N, [a | N1]) :- noun(N, N1).
noun(N, [an | N1]) :- noun(N, N1).

% ------ end of phrases parses ---------


% ---------------- start ---------------
start:-
    write("Hello, thank you for visiting Swaccessories Shop!"),
    nl,
    write("How can I help you?"),
    nl,
    write("Please type your question or type 'contact us'"),
    prompt(_, 'Type here'),
    read(ReadUser),
    ask(ReadUser).
% ------------- end of start ------------


% ---------- ask contact us ----------
askContact:-
    write("You can leave us an email at Swaccessories@example.com
           or phone us at 666-666-6666.").
% ---------- ask contact us ----------


% ------------- ask for -------------
askfor(NP):-
    %write(NP),
    noun(O, NP), 
    istype(O, _T),
    %write(O),
    write("Here is the list of products that are available for purchases."),
    nl,
    forall(prop(O, brand, X), (write(X), nl)),
    write("Which one are you interested in?"),
    nl,
    prompt(_, 'Type here'),
    read(ReadUser),
    ask(ReadUser).

askfor(NP):-
    %write(NP),
    noun(O, NP),
    %write(O),
    isbrand(O, _T),
    %write(O),
    write("Yes, the product is available at "),
    prop(O, price, P),
    write(P),
    nl,
    prop(D, brand, O),
    write("Please go to the "),
    write(D),
    write(" section in our website to find the product."),
    nl.

askfor(NP):-
    %write(NP),
    noun(O, NP),
    %write(O),
    \+istype(O, _X),
    \+isbrand(O, _Y),
    write("Sorry, the product that you are looking for is not available.").
% ---------- end of ask for ----------


% -------------- who am i --------------
whoami :- 
    prop(chatbot, identity, I), 
    write("I am "),
    write(I), 
    nl,
    start.
% ----------- end of who am i -----------


% ----------------- ask -----------------
ask(NP):-
    (   (   L=([who, are, you]),
       		(NP == L ->  whoami)	)
    ->  true;
        (	L=([contact, us]),
       		(NP == L ->  askContact)	)
    ->true;
    	(   checkask(NP)	)
    ->  true
    ). 


checkask([H|T]):-
    (   
    	( 
        	prop(_M, brand, N),
     		H == N -> askfor([H]);
    		ask(T)
        ) 
    	->  true;
    	( 
        	prop(M, brand, _N),
     		H == M -> askfor([H]);
    		ask(T)
        ) 
    	->  true;
    	(   
    		write("Sorry, I seem to can't read that question."),
                   nl,
            write("Please check the spelling, 
                 use important keywords of the products or 
                 type'contact us'")
        )
    ).  
% ------------- end of ask --------------

var Prototype = {
	Version :"1.4.0",
	ScriptFragment :"(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)",
	emptyFunction : function() {
	},
	K : function(a) {
		return a;
	}
};
var Class = {
	create : function() {
		return function() {
			this.initialize.apply(this, arguments);
		};
	}
};
var Abstract = new Object();
Object.extend = function(a, b) {
	for (property in b) {
		a[property] = b[property];
	}
	return a;
};
Object.inspect = function(a) {
	try {
		if (a == undefined) {
			return "undefined";
		}
		if (a == null) {
			return "null";
		}
		return a.inspect ? a.inspect() : a.toString();
	} catch (b) {
		if (b instanceof RangeError) {
			return "...";
		}
		throw b;
	}
};
Function.prototype.bind = function() {
	var a = this, c = $A(arguments), b = c.shift();
	return function() {
		return a.apply(b, c.concat($A(arguments)));
	};
};
Function.prototype.bindAsEventListener = function(b) {
	var a = this;
	return function(c) {
		return a.call(b, c || window.event);
	};
};
Object.extend(Number.prototype, {
	toColorPart : function() {
		var a = this.toString(16);
		if (this < 16) {
			return "0" + a;
		}
		return a;
	},
	succ : function() {
		return this + 1;
	},
	times : function(a) {
		$R(0, this, true).each(a);
		return this;
	}
});
var Try = {
	these : function() {
		var c;
		for ( var b = 0; b < arguments.length; b++) {
			var a = arguments[b];
			try {
				c = a();
				break;
			} catch (d) {
			}
		}
		return c;
	}
};
var PeriodicalExecuter = Class.create();
PeriodicalExecuter.prototype = {
	initialize : function(b, a) {
		this.callback = b;
		this.frequency = a;
		this.currentlyExecuting = false;
		this.registerCallback();
	},
	registerCallback : function() {
		setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
	},
	onTimerEvent : function() {
		if (!this.currentlyExecuting) {
			try {
				this.currentlyExecuting = true;
				this.callback();
			} finally {
				this.currentlyExecuting = false;
			}
		}
	}
};
function $() {
	var c = new Array();
	for ( var b = 0; b < arguments.length; b++) {
		var a = arguments[b];
		if (typeof a == "string") {
			a = document.getElementById(a);
		}
		if (arguments.length == 1) {
			return a;
		}
		c.push(a);
	}
	return c;
}
Object.extend(String.prototype, {
	stripTags : function() {
		return this.replace(/<\/?[^>]+>/gi, "");
	},
	stripScripts : function() {
		return this.replace(new RegExp(Prototype.ScriptFragment, "img"), "");
	},
	extractScripts : function() {
		var b = new RegExp(Prototype.ScriptFragment, "img");
		var a = new RegExp(Prototype.ScriptFragment, "im");
		return (this.match(b) || []).map( function(c) {
			return (c.match(a) || [ "", "" ])[1];
		});
	},
	evalScripts : function() {
		return this.extractScripts().map(eval);
	},
	escapeHTML : function() {
		var b = document.createElement("div");
		var a = document.createTextNode(this);
		b.appendChild(a);
		return b.innerHTML;
	},
	unescapeHTML : function() {
		var a = document.createElement("div");
		a.innerHTML = this.stripTags();
		return a.childNodes[0] ? a.childNodes[0].nodeValue : "";
	},
	toQueryParams : function() {
		var a = this.match(/^\??(.*)$/)[1].split("&");
		return a.inject( {}, function(d, b) {
			var c = b.split("=");
			d[c[0]] = c[1];
			return d;
		});
	},
	toArray : function() {
		return this.split("");
	},
	camelize : function() {
		var d = this.split("-");
		if (d.length == 1) {
			return d[0];
		}
		var b = this.indexOf("-") == 0 ? d[0].charAt(0).toUpperCase()
				+ d[0].substring(1) : d[0];
		for ( var c = 1, a = d.length; c < a; c++) {
			var e = d[c];
			b += e.charAt(0).toUpperCase() + e.substring(1);
		}
		return b;
	},
	inspect : function() {
		return "'" + this.replace("\\", "\\\\").replace("'", "\\'") + "'";
	}
});
String.prototype.parseQuery = String.prototype.toQueryParams;
var $break = new Object();
var $continue = new Object();
var Enumerable = {
	each : function(b) {
		var a = 0;
		try {
			this._each( function(d) {
				try {
					b(d, a++);
				} catch (f) {
					if (f != $continue) {
						throw f;
					}
				}
			});
		} catch (c) {
			if (c != $break) {
				throw c;
			}
		}
	},
	all : function(b) {
		var a = true;
		this.each( function(d, c) {
			a = a && !!(b || Prototype.K)(d, c);
			if (!a) {
				throw $break;
			}
		});
		return a;
	},
	any : function(b) {
		var a = true;
		this.each( function(d, c) {
			if (a = !!(b || Prototype.K)(d, c)) {
				throw $break;
			}
		});
		return a;
	},
	collect : function(b) {
		var a = [];
		this.each( function(d, c) {
			a.push(b(d, c));
		});
		return a;
	},
	detect : function(b) {
		var a;
		this.each( function(d, c) {
			if (b(d, c)) {
				a = d;
				throw $break;
			}
		});
		return a;
	},
	findAll : function(b) {
		var a = [];
		this.each( function(d, c) {
			if (b(d, c)) {
				a.push(d);
			}
		});
		return a;
	},
	grep : function(c, b) {
		var a = [];
		this.each( function(f, e) {
			var d = f.toString();
			if (d.match(c)) {
				a.push((b || Prototype.K)(f, e));
			}
		});
		return a;
	},
	include : function(a) {
		var b = false;
		this.each( function(c) {
			if (c == a) {
				b = true;
				throw $break;
			}
		});
		return b;
	},
	inject : function(a, b) {
		this.each( function(d, c) {
			a = b(a, d, c);
		});
		return a;
	},
	invoke : function(b) {
		var a = $A(arguments).slice(1);
		return this.collect( function(c) {
			return c[b].apply(c, a);
		});
	},
	max : function(b) {
		var a;
		this.each( function(d, c) {
			d = (b || Prototype.K)(d, c);
			if (d >= (a || d)) {
				a = d;
			}
		});
		return a;
	},
	min : function(b) {
		var a;
		this.each( function(d, c) {
			d = (b || Prototype.K)(d, c);
			if (d <= (a || d)) {
				a = d;
			}
		});
		return a;
	},
	partition : function(c) {
		var b = [], a = [];
		this.each( function(e, d) {
			((c || Prototype.K)(e, d) ? b : a).push(e);
		});
		return [ b, a ];
	},
	pluck : function(b) {
		var a = [];
		this.each( function(d, c) {
			a.push(d[b]);
		});
		return a;
	},
	reject : function(b) {
		var a = [];
		this.each( function(d, c) {
			if (!b(d, c)) {
				a.push(d);
			}
		});
		return a;
	},
	sortBy : function(a) {
		return this.collect( function(c, b) {
			return {
				value :c,
				criteria :a(c, b)
			};
		}).sort( function(f, e) {
			var d = f.criteria, c = e.criteria;
			return d < c ? -1 : d > c ? 1 : 0;
		}).pluck("value");
	},
	toArray : function() {
		return this.collect(Prototype.K);
	},
	zip : function() {
		var b = Prototype.K, a = $A(arguments);
		if (typeof a.last() == "function") {
			b = a.pop();
		}
		var c = [ this ].concat(a).map($A);
		return this.map( function(e, d) {
			b(e = c.pluck(d));
			return e;
		});
	},
	inspect : function() {
		return "#<Enumerable:" + this.toArray().inspect() + ">";
	}
};
Object.extend(Enumerable, {
	map :Enumerable.collect,
	find :Enumerable.detect,
	select :Enumerable.findAll,
	member :Enumerable.include,
	entries :Enumerable.toArray
});
var $A = Array.from = function(c) {
	if (!c) {
		return [];
	}
	if (c.toArray) {
		return c.toArray();
	} else {
		var b = [];
		for ( var a = 0; a < c.length; a++) {
			b.push(c[a]);
		}
		return b;
	}
};
Object.extend(Array.prototype, Enumerable);
Array.prototype._reverse = Array.prototype.reverse;
Object.extend(Array.prototype, {
	_each : function(b) {
		for ( var a = 0; a < this.length; a++) {
			b(this[a]);
		}
	},
	clear : function() {
		this.length = 0;
		return this;
	},
	first : function() {
		return this[0];
	},
	last : function() {
		return this[this.length - 1];
	},
	compact : function() {
		return this.select( function(a) {
			return a != undefined || a != null;
		});
	},
	flatten : function() {
		return this.inject( [], function(b, a) {
			return b.concat(a.constructor == Array ? a.flatten() : [ a ]);
		});
	},
	without : function() {
		var a = $A(arguments);
		return this.select( function(b) {
			return !a.include(b);
		});
	},
	indexOf : function(a) {
		for ( var b = 0; b < this.length; b++) {
			if (this[b] == a) {
				return b;
			}
		}
		return -1;
	},
	reverse : function(a) {
		return (a !== false ? this : this.toArray())._reverse();
	},
	shift : function() {
		var a = this[0];
		for ( var b = 0; b < this.length - 1; b++) {
			this[b] = this[b + 1];
		}
		this.length--;
		return a;
	},
	inspect : function() {
		return "[" + this.map(Object.inspect).join(", ") + "]";
	}
});
var Hash = {
	_each : function(a) {
		for (key in this) {
			var b = this[key];
			if (typeof b == "function") {
				continue;
			}
			var c = [ key, b ];
			c.key = key;
			c.value = b;
			a(c);
		}
	},
	keys : function() {
		return this.pluck("key");
	},
	values : function() {
		return this.pluck("value");
	},
	merge : function(a) {
		return $H(a).inject($H(this), function(b, c) {
			b[c.key] = c.value;
			return b;
		});
	},
	toQueryString : function() {
		return this.map( function(a) {
			return a.map(encodeURIComponent).join("=");
		}).join("&");
	},
	inspect : function() {
		return "#<Hash:{" + this.map( function(a) {
			return a.map(Object.inspect).join(": ");
		}).join(", ") + "}>";
	}
};
function $H(a) {
	var b = Object.extend( {}, a || {});
	Object.extend(b, Enumerable);
	Object.extend(b, Hash);
	return b;
}
ObjectRange = Class.create();
Object.extend(ObjectRange.prototype, Enumerable);
Object.extend(ObjectRange.prototype, {
	initialize : function(c, a, b) {
		this.start = c;
		this.end = a;
		this.exclusive = b;
	},
	_each : function(a) {
		var b = this.start;
		do {
			a(b);
			b = b.succ();
		} while (this.include(b));
	},
	include : function(a) {
		if (a < this.start) {
			return false;
		}
		if (this.exclusive) {
			return a < this.end;
		}
		return a <= this.end;
	}
});
var $R = function(c, a, b) {
	return new ObjectRange(c, a, b);
};
var Ajax = {
	getTransport : function() {
		return Try.these( function() {
			return new ActiveXObject("Msxml2.XMLHTTP");
		}, function() {
			return new ActiveXObject("Microsoft.XMLHTTP");
		}, function() {
			return new XMLHttpRequest();
		}) || false;
	},
	activeRequestCount :0
};
Ajax.Responders = {
	responders : [],
	_each : function(a) {
		this.responders._each(a);
	},
	register : function(a) {
		if (!this.include(a)) {
			this.responders.push(a);
		}
	},
	unregister : function(a) {
		this.responders = this.responders.without(a);
	},
	dispatch : function(d, b, c, a) {
		this.each( function(f) {
			if (f[d] && typeof f[d] == "function") {
				try {
					f[d].apply(f, [ b, c, a ]);
				} catch (g) {
				}
			}
		});
	}
};
Object.extend(Ajax.Responders, Enumerable);
Ajax.Responders.register( {
	onCreate : function() {
		Ajax.activeRequestCount++;
	},
	onComplete : function() {
		Ajax.activeRequestCount--;
	}
});
Ajax.Base = function() {
};
Ajax.Base.prototype = {
	setOptions : function(a) {
		this.options = {
			method :"post",
			asynchronous :true,
			parameters :""
		};
		Object.extend(this.options, a || {});
	},
	responseIsSuccess : function() {
		return this.transport.status == undefined
				|| this.transport.status == 0
				|| (this.transport.status >= 200 && this.transport.status < 300);
	},
	responseIsFailure : function() {
		return !this.responseIsSuccess();
	}
};
Ajax.Request = Class.create();
Ajax.Request.Events = [ "Uninitialized", "Loading", "Loaded", "Interactive",
		"Complete" ];
Ajax.Request.prototype = Object
		.extend(
				new Ajax.Base(),
				{
					initialize : function(b, a) {
						this.transport = Ajax.getTransport();
						this.setOptions(a);
						this.request(b);
					},
					request : function(b) {
						var c = this.options.parameters || "";
						if (c.length > 0) {
							c += "&_=";
						}
						try {
							this.url = b;
							if (this.options.method == "get" && c.length > 0) {
								this.url += (this.url.match(/\?/) ? "&" : "?")
										+ c;
							}
							Ajax.Responders.dispatch("onCreate", this,
									this.transport);
							this.transport.open(this.options.method, this.url,
									this.options.asynchronous);
							if (this.options.asynchronous) {
								this.transport.onreadystatechange = this.onStateChange
										.bind(this);
								setTimeout(( function() {
									this.respondToReadyState(1);
								}).bind(this), 10);
							}
							this.setRequestHeaders();
							var a = this.options.postBody ? this.options.postBody
									: c;
							this.transport
									.send(this.options.method == "post" ? a
											: null);
						} catch (d) {
							this.dispatchException(d);
						}
					},
					setRequestHeaders : function() {
						var b = [ "X-Requested-With", "XMLHttpRequest",
								"X-Prototype-Version", Prototype.Version ];
						if (this.options.method == "post") {
							b.push("Content-type",
									"application/x-www-form-urlencoded");
							if (this.transport.overrideMimeType) {
								b.push("Connection", "close");
							}
						}
						if (this.options.requestHeaders) {
							b.push.apply(b, this.options.requestHeaders);
						}
						for ( var a = 0; a < b.length; a += 2) {
							this.transport.setRequestHeader(b[a], b[a + 1]);
						}
					},
					onStateChange : function() {
						var a = this.transport.readyState;
						if (a != 1) {
							this.respondToReadyState(this.transport.readyState);
						}
					},
					header : function(a) {
						try {
							return this.transport.getResponseHeader(a);
						} catch (b) {
						}
					},
					evalJSON : function() {
						try {
							return eval(this.header("X-JSON"));
						} catch (e) {
						}
					},
					evalResponse : function() {
						try {
							return eval(this.transport.responseText);
						} catch (e) {
							this.dispatchException(e);
						}
					},
					respondToReadyState : function(a) {
						var c = Ajax.Request.Events[a];
						var f = this.transport, b = this.evalJSON();
						if (c == "Complete") {
							try {
								(this.options["on" + this.transport.status]
										|| this.options["on"
												+ (this.responseIsSuccess() ? "Success"
														: "Failure")] || Prototype.emptyFunction)
										(f, b);
							} catch (d) {
								this.dispatchException(d);
							}
							if ((this.header("Content-type") || "")
									.match(/^text\/javascript/i)) {
								this.evalResponse();
							}
						}
						try {
							(this.options["on" + c] || Prototype.emptyFunction)
									(f, b);
							Ajax.Responders.dispatch("on" + c, this, f, b);
						} catch (d) {
							this.dispatchException(d);
						}
						if (c == "Complete") {
							this.transport.onreadystatechange = Prototype.emptyFunction;
						}
					},
					dispatchException : function(a) {
						(this.options.onException || Prototype.emptyFunction)(
								this, a);
						Ajax.Responders.dispatch("onException", this, a);
					}
				});
Ajax.Updater = Class.create();
Object.extend(Object.extend(Ajax.Updater.prototype, Ajax.Request.prototype), {
	initialize : function(a, c, b) {
		this.containers = {
			success :a.success ? $(a.success) : $(a),
			failure :a.failure ? $(a.failure) : (a.success ? null : $(a))
		};
		this.transport = Ajax.getTransport();
		this.setOptions(b);
		var d = this.options.onComplete || Prototype.emptyFunction;
		this.options.onComplete = ( function(f, e) {
			this.updateContent();
			d(f, e);
		}).bind(this);
		this.request(c);
	},
	updateContent : function() {
		var b = this.responseIsSuccess() ? this.containers.success
				: this.containers.failure;
		var a = this.transport.responseText;
		if (!this.options.evalScripts) {
			a = a.stripScripts();
		}
		if (b) {
			if (this.options.insertion) {
				new this.options.insertion(b, a);
			} else {
				Element.update(b, a);
			}
		}
		if (this.responseIsSuccess()) {
			if (this.onComplete) {
				setTimeout(this.onComplete.bind(this), 10);
			}
		}
	}
});
Ajax.PeriodicalUpdater = Class.create();
Ajax.PeriodicalUpdater.prototype = Object.extend(new Ajax.Base(),
		{
			initialize : function(a, c, b) {
				this.setOptions(b);
				this.onComplete = this.options.onComplete;
				this.frequency = (this.options.frequency || 2);
				this.decay = (this.options.decay || 1);
				this.updater = {};
				this.container = a;
				this.url = c;
				this.start();
			},
			start : function() {
				this.options.onComplete = this.updateComplete.bind(this);
				this.onTimerEvent();
			},
			stop : function() {
				this.updater.onComplete = undefined;
				clearTimeout(this.timer);
				(this.onComplete || Prototype.emptyFunction).apply(this,
						arguments);
			},
			updateComplete : function(a) {
				if (this.options.decay) {
					this.decay = (a.responseText == this.lastText ? this.decay
							* this.options.decay : 1);
					this.lastText = a.responseText;
				}
				this.timer = setTimeout(this.onTimerEvent.bind(this),
						this.decay * this.frequency * 1000);
			},
			onTimerEvent : function() {
				this.updater = new Ajax.Updater(this.container, this.url,
						this.options);
			}
		});
document.getElementsByClassName = function(c, a) {
	var b = ($(a) || document.body).getElementsByTagName("*");
	return $A(b).inject( [], function(d, e) {
		if (e.className.match(new RegExp("(^|\\s)" + c + "(\\s|$)"))) {
			d.push(e);
		}
		return d;
	});
};
if (!window.Element) {
	var Element = new Object();
}
Object
		.extend(
				Element,
				{
					visible : function(a) {
						return $(a).style.display != "none";
					},
					toggle : function() {
						for ( var b = 0; b < arguments.length; b++) {
							var a = $(arguments[b]);
							Element[Element.visible(a) ? "hide" : "show"](a);
						}
					},
					hide : function() {
						for ( var b = 0; b < arguments.length; b++) {
							var a = $(arguments[b]);
							a.style.display = "none";
						}
					},
					show : function() {
						for ( var b = 0; b < arguments.length; b++) {
							var a = $(arguments[b]);
							a.style.display = "";
						}
					},
					remove : function(a) {
						a = $(a);
						a.parentNode.removeChild(a);
					},
					update : function(b, a) {
						$(b).innerHTML = a.stripScripts();
						setTimeout( function() {
							a.evalScripts();
						}, 10);
					},
					getHeight : function(a) {
						a = $(a);
						return a.offsetHeight;
					},
					classNames : function(a) {
						return new Element.ClassNames(a);
					},
					hasClassName : function(a, b) {
						if (!(a = $(a))) {
							return;
						}
						return Element.classNames(a).include(b);
					},
					addClassName : function(a, b) {
						if (!(a = $(a))) {
							return;
						}
						return Element.classNames(a).add(b);
					},
					removeClassName : function(a, b) {
						if (!(a = $(a))) {
							return;
						}
						return Element.classNames(a).remove(b);
					},
					cleanWhitespace : function(b) {
						b = $(b);
						for ( var a = 0; a < b.childNodes.length; a++) {
							var c = b.childNodes[a];
							if (c.nodeType == 3 && !/\S/.test(c.nodeValue)) {
								Element.remove(c);
							}
						}
					},
					empty : function(a) {
						return $(a).innerHTML.match(/^\s*$/);
					},
					scrollTo : function(b) {
						b = $(b);
						var a = b.x ? b.x : b.offsetLeft, c = b.y ? b.y
								: b.offsetTop;
						window.scrollTo(a, c);
					},
					getStyle : function(b, c) {
						b = $(b);
						var d = b.style[c.camelize()];
						if (!d) {
							if (document.defaultView
									&& document.defaultView.getComputedStyle) {
								var a = document.defaultView.getComputedStyle(
										b, null);
								d = a ? a.getPropertyValue(c) : null;
							} else {
								if (b.currentStyle) {
									d = b.currentStyle[c.camelize()];
								}
							}
						}
						if (window.opera
								&& [ "left", "top", "right", "bottom" ]
										.include(c)) {
							if (Element.getStyle(b, "position") == "static") {
								d = "auto";
							}
						}
						return d == "auto" ? null : d;
					},
					setStyle : function(a, b) {
						a = $(a);
						for (name in b) {
							a.style[name.camelize()] = b[name];
						}
					},
					getDimensions : function(b) {
						b = $(b);
						if (Element.getStyle(b, "display") != "none") {
							return {
								width :b.offsetWidth,
								height :b.offsetHeight
							};
						}
						var a = b.style;
						var e = a.visibility;
						var c = a.position;
						a.visibility = "hidden";
						a.position = "absolute";
						a.display = "";
						var f = b.clientWidth;
						var d = b.clientHeight;
						a.display = "none";
						a.position = c;
						a.visibility = e;
						return {
							width :f,
							height :d
						};
					},
					makePositioned : function(a) {
						a = $(a);
						var b = Element.getStyle(a, "position");
						if (b == "static" || !b) {
							a._madePositioned = true;
							a.style.position = "relative";
							if (window.opera) {
								a.style.top = 0;
								a.style.left = 0;
							}
						}
					},
					undoPositioned : function(a) {
						a = $(a);
						if (a._madePositioned) {
							a._madePositioned = undefined;
							a.style.position = a.style.top = a.style.left = a.style.bottom = a.style.right = "";
						}
					},
					makeClipping : function(a) {
						a = $(a);
						if (a._overflow) {
							return;
						}
						a._overflow = a.style.overflow;
						if ((Element.getStyle(a, "overflow") || "visible") != "hidden") {
							a.style.overflow = "hidden";
						}
					},
					undoClipping : function(a) {
						a = $(a);
						if (a._overflow) {
							return;
						}
						a.style.overflow = a._overflow;
						a._overflow = undefined;
					}
				});
var Toggle = new Object();
Toggle.display = Element.toggle;
Abstract.Insertion = function(a) {
	this.adjacency = a;
};
Abstract.Insertion.prototype = {
	initialize : function(a, b) {
		this.element = $(a);
		this.content = b.stripScripts();
		if (this.adjacency && this.element.insertAdjacentHTML) {
			try {
				this.element.insertAdjacentHTML(this.adjacency, this.content);
			} catch (c) {
				if (this.element.tagName.toLowerCase() == "tbody") {
					this.insertContent(this.contentFromAnonymousTable());
				} else {
					throw c;
				}
			}
		} else {
			this.range = this.element.ownerDocument.createRange();
			if (this.initializeRange) {
				this.initializeRange();
			}
			this.insertContent( [ this.range
					.createContextualFragment(this.content) ]);
		}
		setTimeout( function() {
			b.evalScripts();
		}, 10);
	},
	contentFromAnonymousTable : function() {
		var a = document.createElement("div");
		a.innerHTML = "<table><tbody>" + this.content + "</tbody></table>";
		return $A(a.childNodes[0].childNodes[0].childNodes);
	}
};
var Insertion = new Object();
Insertion.Before = Class.create();
Insertion.Before.prototype = Object.extend(
		new Abstract.Insertion("beforeBegin"), {
			initializeRange : function() {
				this.range.setStartBefore(this.element);
			},
			insertContent : function(a) {
				a.each(( function(b) {
					this.element.parentNode.insertBefore(b, this.element);
				}).bind(this));
			}
		});
Insertion.Top = Class.create();
Insertion.Top.prototype = Object.extend(new Abstract.Insertion("afterBegin"), {
	initializeRange : function() {
		this.range.selectNodeContents(this.element);
		this.range.collapse(true);
	},
	insertContent : function(a) {
		a.reverse(false).each(( function(b) {
			this.element.insertBefore(b, this.element.firstChild);
		}).bind(this));
	}
});
Insertion.Bottom = Class.create();
Insertion.Bottom.prototype = Object.extend(new Abstract.Insertion("beforeEnd"),
		{
			initializeRange : function() {
				this.range.selectNodeContents(this.element);
				this.range.collapse(this.element);
			},
			insertContent : function(a) {
				a.each(( function(b) {
					this.element.appendChild(b);
				}).bind(this));
			}
		});
Insertion.After = Class.create();
Insertion.After.prototype = Object.extend(new Abstract.Insertion("afterEnd"), {
	initializeRange : function() {
		this.range.setStartAfter(this.element);
	},
	insertContent : function(a) {
		a.each(( function(b) {
			this.element.parentNode.insertBefore(b, this.element.nextSibling);
		}).bind(this));
	}
});
Element.ClassNames = Class.create();
Element.ClassNames.prototype = {
	initialize : function(a) {
		this.element = $(a);
	},
	_each : function(a) {
		this.element.className.split(/\s+/).select( function(b) {
			return b.length > 0;
		})._each(a);
	},
	set : function(a) {
		this.element.className = a;
	},
	add : function(a) {
		if (this.include(a)) {
			return;
		}
		this.set(this.toArray().concat(a).join(" "));
	},
	remove : function(a) {
		if (!this.include(a)) {
			return;
		}
		this.set(this.select( function(b) {
			return b != a;
		}).join(" "));
	},
	toString : function() {
		return this.toArray().join(" ");
	}
};
Object.extend(Element.ClassNames.prototype, Enumerable);
var Field = {
	clear : function() {
		for ( var a = 0; a < arguments.length; a++) {
			$(arguments[a]).value = "";
		}
	},
	focus : function(a) {
		$(a).focus();
	},
	present : function() {
		for ( var a = 0; a < arguments.length; a++) {
			if ($(arguments[a]).value == "") {
				return false;
			}
		}
		return true;
	},
	select : function(a) {
		$(a).select();
	},
	activate : function(a) {
		a = $(a);
		a.focus();
		if (a.select) {
			a.select();
		}
	}
};
var Form = {
	serialize : function(d) {
		var e = Form.getElements($(d));
		var c = new Array();
		for ( var b = 0; b < e.length; b++) {
			var a = Form.Element.serialize(e[b]);
			if (a) {
				c.push(a);
			}
		}
		return c.join("&");
	},
	getElements : function(b) {
		b = $(b);
		var c = new Array();
		for (tagName in Form.Element.Serializers) {
			var d = b.getElementsByTagName(tagName);
			for ( var a = 0; a < d.length; a++) {
				c.push(d[a]);
			}
		}
		return c;
	},
	getInputs : function(f, c, d) {
		f = $(f);
		var a = f.getElementsByTagName("input");
		if (!c && !d) {
			return a;
		}
		var g = new Array();
		for ( var e = 0; e < a.length; e++) {
			var b = a[e];
			if ((c && b.type != c) || (d && b.name != d)) {
				continue;
			}
			g.push(b);
		}
		return g;
	},
	disable : function(c) {
		var d = Form.getElements(c);
		for ( var b = 0; b < d.length; b++) {
			var a = d[b];
			a.blur();
			a.disabled = "true";
		}
	},
	enable : function(c) {
		var d = Form.getElements(c);
		for ( var b = 0; b < d.length; b++) {
			var a = d[b];
			a.disabled = "";
		}
	},
	findFirstElement : function(a) {
		return Form.getElements(a).find(
				function(b) {
					return b.type != "hidden"
							&& !b.disabled
							&& [ "input", "select", "textarea" ]
									.include(b.tagName.toLowerCase());
				});
	},
	focusFirstElement : function(a) {
		Field.activate(Form.findFirstElement(a));
	},
	reset : function(a) {
		$(a).reset();
	}
};
Form.Element = {
	serialize : function(b) {
		b = $(b);
		var d = b.tagName.toLowerCase();
		var c = Form.Element.Serializers[d](b);
		if (c) {
			var a = encodeURIComponent(c[0]);
			if (a.length == 0) {
				return;
			}
			if (c[1].constructor != Array) {
				c[1] = [ c[1] ];
			}
			return c[1].map( function(e) {
				return a + "=" + encodeURIComponent(e);
			}).join("&");
		}
	},
	getValue : function(a) {
		a = $(a);
		var c = a.tagName.toLowerCase();
		var b = Form.Element.Serializers[c](a);
		if (b) {
			return b[1];
		}
	}
};
Form.Element.Serializers = {
	input : function(a) {
		switch (a.type.toLowerCase()) {
		case "submit":
		case "hidden":
		case "password":
		case "text":
			return Form.Element.Serializers.textarea(a);
		case "checkbox":
		case "radio":
			return Form.Element.Serializers.inputSelector(a);
		}
		return false;
	},
	inputSelector : function(a) {
		if (a.checked) {
			return [ a.name, a.value ];
		}
	},
	textarea : function(a) {
		return [ a.name, a.value ];
	},
	select : function(a) {
		return Form.Element.Serializers[a.type == "select-one" ? "selectOne"
				: "selectMany"](a);
	},
	selectOne : function(c) {
		var d = "", b, a = c.selectedIndex;
		if (a >= 0) {
			b = c.options[a];
			d = b.value;
			if (!d && !("value" in b)) {
				d = b.text;
			}
		}
		return [ c.name, d ];
	},
	selectMany : function(c) {
		var d = new Array();
		for ( var b = 0; b < c.length; b++) {
			var a = c.options[b];
			if (a.selected) {
				var e = a.value;
				if (!e && !("value" in a)) {
					e = a.text;
				}
				d.push(e);
			}
		}
		return [ c.name, d ];
	}
};
var $F = Form.Element.getValue;
Abstract.TimedObserver = function() {
};
Abstract.TimedObserver.prototype = {
	initialize : function(a, b, c) {
		this.frequency = b;
		this.element = $(a);
		this.callback = c;
		this.lastValue = this.getValue();
		this.registerCallback();
	},
	registerCallback : function() {
		setInterval(this.onTimerEvent.bind(this), this.frequency * 1000);
	},
	onTimerEvent : function() {
		var a = this.getValue();
		if (this.lastValue != a) {
			this.callback(this.element, a);
			this.lastValue = a;
		}
	}
};
Form.Element.Observer = Class.create();
Form.Element.Observer.prototype = Object.extend(new Abstract.TimedObserver(), {
	getValue : function() {
		return Form.Element.getValue(this.element);
	}
});
Form.Observer = Class.create();
Form.Observer.prototype = Object.extend(new Abstract.TimedObserver(), {
	getValue : function() {
		return Form.serialize(this.element);
	}
});
Abstract.EventObserver = function() {
};
Abstract.EventObserver.prototype = {
	initialize : function(a, b) {
		this.element = $(a);
		this.callback = b;
		this.lastValue = this.getValue();
		if (this.element.tagName.toLowerCase() == "form") {
			this.registerFormCallbacks();
		} else {
			this.registerCallback(this.element);
		}
	},
	onElementEvent : function() {
		var a = this.getValue();
		if (this.lastValue != a) {
			this.callback(this.element, a);
			this.lastValue = a;
		}
	},
	registerFormCallbacks : function() {
		var b = Form.getElements(this.element);
		for ( var a = 0; a < b.length; a++) {
			this.registerCallback(b[a]);
		}
	},
	registerCallback : function(a) {
		if (a.type) {
			switch (a.type.toLowerCase()) {
			case "checkbox":
			case "radio":
				Event.observe(a, "click", this.onElementEvent.bind(this));
				break;
			case "password":
			case "text":
			case "textarea":
			case "select-one":
			case "select-multiple":
				Event.observe(a, "change", this.onElementEvent.bind(this));
				break;
			}
		}
	}
};
Form.Element.EventObserver = Class.create();
Form.Element.EventObserver.prototype = Object.extend(
		new Abstract.EventObserver(), {
			getValue : function() {
				return Form.Element.getValue(this.element);
			}
		});
Form.EventObserver = Class.create();
Form.EventObserver.prototype = Object.extend(new Abstract.EventObserver(), {
	getValue : function() {
		return Form.serialize(this.element);
	}
});
if (!window.Event) {
	var Event = new Object();
}
Object
		.extend(
				Event,
				{
					KEY_BACKSPACE :8,
					KEY_TAB :9,
					KEY_RETURN :13,
					KEY_ESC :27,
					KEY_LEFT :37,
					KEY_UP :38,
					KEY_RIGHT :39,
					KEY_DOWN :40,
					KEY_DELETE :46,
					element : function(a) {
						return a.target || a.srcElement;
					},
					isLeftClick : function(a) {
						return (((a.which) && (a.which == 1)) || ((a.button) && (a.button == 1)));
					},
					pointerX : function(a) {
						return a.pageX
								|| (a.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft));
					},
					pointerY : function(a) {
						return a.pageY
								|| (a.clientY + (document.documentElement.scrollTop || document.body.scrollTop));
					},
					stop : function(a) {
						if (a.preventDefault) {
							a.preventDefault();
							a.stopPropagation();
						} else {
							a.returnValue = false;
							a.cancelBubble = true;
						}
					},
					findElement : function(c, b) {
						var a = Event.element(c);
						while (a.parentNode
								&& (!a.tagName || (a.tagName.toUpperCase() != b
										.toUpperCase()))) {
							a = a.parentNode;
						}
						return a;
					},
					observers :false,
					_observeAndCache : function(d, c, b, a) {
						if (!this.observers) {
							this.observers = [];
						}
						if (d.addEventListener) {
							this.observers.push( [ d, c, b, a ]);
							d.addEventListener(c, b, a);
						} else {
							if (d.attachEvent) {
								this.observers.push( [ d, c, b, a ]);
								d.attachEvent("on" + c, b);
							}
						}
					},
					unloadCache : function() {
						if (!Event.observers) {
							return;
						}
						for ( var a = 0; a < Event.observers.length; a++) {
							Event.stopObserving.apply(this, Event.observers[a]);
							Event.observers[a][0] = null;
						}
						Event.observers = false;
					},
					observe : function(d, c, b, a) {
						var d = $(d);
						a = a || false;
						if (c == "keypress"
								&& (navigator.appVersion
										.match(/Konqueror|Safari|KHTML/) || d.attachEvent)) {
							c = "keydown";
						}
						this._observeAndCache(d, c, b, a);
					},
					stopObserving : function(d, c, b, a) {
						var d = $(d);
						a = a || false;
						if (c == "keypress"
								&& (navigator.appVersion
										.match(/Konqueror|Safari|KHTML/) || d.detachEvent)) {
							c = "keydown";
						}
						if (d.removeEventListener) {
							d.removeEventListener(c, b, a);
						} else {
							if (d.detachEvent) {
								d.detachEvent("on" + c, b);
							}
						}
					}
				});
Event.observe(window, "unload", Event.unloadCache, false);
var Position = {
	includeScrollOffsets :false,
	prepare : function() {
		this.deltaX = window.pageXOffset || document.documentElement.scrollLeft
				|| document.body.scrollLeft || 0;
		this.deltaY = window.pageYOffset || document.documentElement.scrollTop
				|| document.body.scrollTop || 0;
	},
	realOffset : function(b) {
		var a = 0, c = 0;
		do {
			a += b.scrollTop || 0;
			c += b.scrollLeft || 0;
			b = b.parentNode;
		} while (b);
		return [ c, a ];
	},
	cumulativeOffset : function(b) {
		var a = 0, c = 0;
		do {
			a += b.offsetTop || 0;
			c += b.offsetLeft || 0;
			b = b.offsetParent;
		} while (b);
		return [ c, a ];
	},
	positionedOffset : function(b) {
		var a = 0, c = 0;
		do {
			a += b.offsetTop || 0;
			c += b.offsetLeft || 0;
			b = b.offsetParent;
			if (b) {
				p = Element.getStyle(b, "position");
				if (p == "relative" || p == "absolute") {
					break;
				}
			}
		} while (b);
		return [ c, a ];
	},
	offsetParent : function(a) {
		if (a.offsetParent) {
			return a.offsetParent;
		}
		if (a == document.body) {
			return a;
		}
		while ((a = a.parentNode) && a != document.body) {
			if (Element.getStyle(a, "position") != "static") {
				return a;
			}
		}
		return document.body;
	},
	within : function(b, a, c) {
		if (this.includeScrollOffsets) {
			return this.withinIncludingScrolloffsets(b, a, c);
		}
		this.xcomp = a;
		this.ycomp = c;
		this.offset = this.cumulativeOffset(b);
		return (c >= this.offset[1] && c < this.offset[1] + b.offsetHeight
				&& a >= this.offset[0] && a < this.offset[0] + b.offsetWidth);
	},
	withinIncludingScrolloffsets : function(b, a, d) {
		var c = this.realOffset(b);
		this.xcomp = a + c[0] - this.deltaX;
		this.ycomp = d + c[1] - this.deltaY;
		this.offset = this.cumulativeOffset(b);
		return (this.ycomp >= this.offset[1]
				&& this.ycomp < this.offset[1] + b.offsetHeight
				&& this.xcomp >= this.offset[0] && this.xcomp < this.offset[0]
				+ b.offsetWidth);
	},
	overlap : function(b, a) {
		if (!b) {
			return 0;
		}
		if (b == "vertical") {
			return ((this.offset[1] + a.offsetHeight) - this.ycomp)
					/ a.offsetHeight;
		}
		if (b == "horizontal") {
			return ((this.offset[0] + a.offsetWidth) - this.xcomp)
					/ a.offsetWidth;
		}
	},
	clone : function(b, c) {
		b = $(b);
		c = $(c);
		c.style.position = "absolute";
		var a = this.cumulativeOffset(b);
		c.style.top = a[1] + "px";
		c.style.left = a[0] + "px";
		c.style.width = b.offsetWidth + "px";
		c.style.height = b.offsetHeight + "px";
	},
	page : function(d) {
		var a = 0, c = 0;
		var b = d;
		do {
			a += b.offsetTop || 0;
			c += b.offsetLeft || 0;
			if (b.offsetParent == document.body) {
				if (Element.getStyle(b, "position") == "absolute") {
					break;
				}
			}
		} while (b = b.offsetParent);
		b = d;
		do {
			a -= b.scrollTop || 0;
			c -= b.scrollLeft || 0;
		} while (b = b.parentNode);
		return [ c, a ];
	},
	clone : function(c, e) {
		var a = Object.extend( {
			setLeft :true,
			setTop :true,
			setWidth :true,
			setHeight :true,
			offsetTop :0,
			offsetLeft :0
		}, arguments[2] || {});
		c = $(c);
		var d = Position.page(c);
		e = $(e);
		var f = [ 0, 0 ];
		var b = null;
		if (Element.getStyle(e, "position") == "absolute") {
			b = Position.offsetParent(e);
			f = Position.page(b);
		}
		if (b == document.body) {
			f[0] -= document.body.offsetLeft;
			f[1] -= document.body.offsetTop;
		}
		if (a.setLeft) {
			e.style.left = (d[0] - f[0] + a.offsetLeft) + "px";
		}
		if (a.setTop) {
			e.style.top = (d[1] - f[1] + a.offsetTop) + "px";
		}
		if (a.setWidth) {
			e.style.width = c.offsetWidth + "px";
		}
		if (a.setHeight) {
			e.style.height = c.offsetHeight + "px";
		}
	},
	absolutize : function(b) {
		b = $(b);
		if (b.style.position == "absolute") {
			return;
		}
		Position.prepare();
		var d = Position.positionedOffset(b);
		var f = d[1];
		var e = d[0];
		var c = b.clientWidth;
		var a = b.clientHeight;
		b._originalLeft = e - parseFloat(b.style.left || 0);
		b._originalTop = f - parseFloat(b.style.top || 0);
		b._originalWidth = b.style.width;
		b._originalHeight = b.style.height;
		b.style.position = "absolute";
		b.style.top = f + "px";
		b.style.left = e + "px";
		b.style.width = c + "px";
		b.style.height = a + "px";
	},
	relativize : function(a) {
		a = $(a);
		if (a.style.position == "relative") {
			return;
		}
		Position.prepare();
		a.style.position = "relative";
		var c = parseFloat(a.style.top || 0) - (a._originalTop || 0);
		var b = parseFloat(a.style.left || 0) - (a._originalLeft || 0);
		a.style.top = c + "px";
		a.style.left = b + "px";
		a.style.height = a._originalHeight;
		a.style.width = a._originalWidth;
	}
};
if (/Konqueror|Safari|KHTML/.test(navigator.userAgent)) {
	Position.cumulativeOffset = function(b) {
		var a = 0, c = 0;
		do {
			a += b.offsetTop || 0;
			c += b.offsetLeft || 0;
			if (b.offsetParent == document.body) {
				if (Element.getStyle(b, "position") == "absolute") {
					break;
				}
			}
			b = b.offsetParent;
		} while (b);
		return [ c, a ];
	};
}